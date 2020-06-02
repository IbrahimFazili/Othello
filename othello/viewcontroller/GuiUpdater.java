package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.*;

import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


import java.util.ArrayList;

/**
 * GuiUpdater receives updates from the boardChangeNotifier and updates the GUI to
 * reflect the changes in the model
 * */
public class GuiUpdater implements Observer {

    // separate references to certain GUI elements for ease of access to them
    private GridPane uiGrid;
    private OthelloController controller;
    private TextField player1, player2;
    private ImageView turn;
    private FlowPane topPane;
    private CountdownTimer p1Timer, p2Timer;
    private int moveCount;
    private TextField countdownText;
    private TextField countdownText2;
    private int[] startingTime = {5, 0};
    //create images for tokens
    private Image img1 = new Image("bToken.png");
    private Image img2 = new Image("wToken.png", 28, 28,
            false, false);
    private HBox P1 = new HBox();
    private HBox P2 = new HBox();

    private final ImageView EMPTY_IMAGE_VIEW = new ImageView();

    public GuiUpdater(GridPane uiGrid, FlowPane topPane, FlowPane rightPane, OthelloController controller) {
        this.uiGrid = uiGrid;
        this.controller = controller;
        this.topPane = topPane;
        this.player1 = (TextField) ((HBox)topPane.getChildren().get(0)).getChildren().get(1);
        this.player2 = (TextField) ((HBox)topPane.getChildren().get(1)).getChildren().get(1);
        this.turn = (ImageView) ((HBox)rightPane.getChildren().get(0)).getChildren().get(1);
        this.countdownText = (TextField) rightPane.getChildren().get(1);
        this.countdownText2 = (TextField) rightPane.getChildren().get(2);
        this.moveCount = 0;
        this.p1Timer = new CountdownTimer(startingTime[0], startingTime[1], 'X');
        this.p1Timer.attach(this);
        this.P1.getChildren().addAll(new ImageView(img1), countdownText);
        this.P2.getChildren().addAll(new ImageView(img2), countdownText2);
        rightPane.getChildren().add(1, P1);
        rightPane.getChildren().add(2, P2);

        this.p2Timer = new CountdownTimer(startingTime[0], startingTime[1], 'O');
        this.p2Timer.attach(this);
    }

    /**
     * Set the starting time for the game. Should only be called during
     * the initialization process of the game
     *
     * @param time starting time for both players in order [P1_min, P1_sec, P2_min, P2_sec]
     * */
    void setStartingTime(int[] time) {
        // set starting time
        this.startingTime[0] = time[0];
        this.startingTime[1] = time[1];
        int playerTwoMin = time[2];
        int playerTwoSec = time[3];

        // detach old observers
        this.p1Timer.detach(this);
        this.p2Timer.detach(this);

        // kill old timer threads
        this.killTimers();

        // initialize new timers
        this.p1Timer = new CountdownTimer(startingTime[0], startingTime[1], 'X');
        this.p1Timer.attach(this);

        this.p2Timer = new CountdownTimer(playerTwoMin, playerTwoSec, 'O');
        this.p2Timer.attach(this);
    }

    /**
     * Start the game timer. Should only be called during the initialization process of the game
     * */
    public void startTimer(){
        if(this.controller.getGameBoard().getWhosTurn() == 'X') { this.p1Timer.startTimer(); }
        else if(this.controller.getGameBoard().getWhosTurn() == 'O') { this.p2Timer.startTimer(); }
    }

    /**
     * Kill timers when application is exiting.
     * */
    public void killTimers() {
        this.p1Timer.killTimer();
        this.p2Timer.killTimer();
    }

    /**
     * Update the main button grid after receiving an update from the model
     *
     * @param notifier the object which notifies this updater of the change on the model
     * @param buttons Array of buttons in the main grid
     * */
    private void updateGridButtons(boardChangeNotifier notifier, Object[] buttons) {
        Button b = null;
        OthelloBoard updatedBoard = notifier.getBoard();
        OthelloBoard oldBoard;

        if(notifier.peekLastState() != null){
            oldBoard = notifier.peekLastState().getBoard();
        }
        else {oldBoard = null;}

        for(int i = 0; i < buttons.length; i++) {
            b = (Button) buttons[i];
            Node temp = b.getGraphic();

            int[] coords = UserInputHandler.parseID(b.getId());

            // non-application threads aren't allowed to setGraphic on buttons apparently
            if(Platform.isFxApplicationThread()) {
                //updates images
                if (updatedBoard.get(coords[0], coords[1]) == 'X'){
                    b.setGraphic(new ImageView (img1));
                    if (oldBoard != null && (updatedBoard.get(coords[0], coords[1]) !=
                            oldBoard.get(coords[0],coords[1]))){
                        animateButton(b);
                    }

                }
                else if (updatedBoard.get(coords[0], coords[1]) == 'O'){
                    b.setGraphic(new ImageView (img2));
                    if (oldBoard != null && (updatedBoard.get(coords[0], coords[1]) !=
                            oldBoard.get(coords[0],coords[1]))){

                        animateButton(b);
                    }
                }
                else {
                    b.setGraphic(this.EMPTY_IMAGE_VIEW);
                }
            }
            else {
                b.setGraphic(this.EMPTY_IMAGE_VIEW);
            }

            // reset btn color (later updated by highlight moves)
            b.setStyle("-fx-background-color: transparent;");
        }


    }

    /**
     * Animate this button with fade animation
     *
     * @param b the button that should be animated
     * */
    private void animateButton (Button b){

        FadeTransition ft = new FadeTransition();

        ft.setNode(b.getGraphic());
        ft.setDuration(new Duration(1000));
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }


    /**
     * Highlight all the valid moves on the board. Updates the border color
     * of the buttons to green
     *
     * @param notifier the observable that sends updates to the GuiUpdater
     * @param buttons array of Buttons that the method updates
     * */
    private void highlightValidMoves(boardChangeNotifier notifier, Object[] buttons) {
        Button b = null;
        ArrayList<Move> validMoves = notifier.getAvailMoves();

        // temporary vars
        int[] coords = null;
        Move thisMove = null;

        Circle hintOutlineCircle = null;

        for(int i = 0; i < validMoves.size(); i++) {
            thisMove = validMoves.get(i);

            // search for the button corresponding to this move
            for(int j = 0; j < buttons.length; j++) {
                b = (Button) buttons[j];
                coords = UserInputHandler.parseID(b.getId());

                hintOutlineCircle = new Circle(14);
                hintOutlineCircle.setFill(Color.TRANSPARENT);
                hintOutlineCircle.setStroke(Color.gray(0.8, 0.4));

                // update color and break inner loop
                if(thisMove.equals(coords)) {
                    b.setGraphic(hintOutlineCircle);
                    break;
                }
            }

        }
    }

    /**
     * Update time on the GUI with the new time
     *
     * @param min new minute value for time
     * @param sec new second value for time
     * @param player the player who's timer needs to be updated
     * */
    private void updateTime(int min, int sec, char player){
        String writableSec = sec < 10 ? "0" + sec : "" + sec;
        if (player == 'X') { this.countdownText.setText("Time: "+ min + ":" + writableSec); }
        else { this.countdownText2.setText("Time: "+ min + ":" + writableSec); }
    }

    /**
     * Update the top flow pane
     *
     * @param notifier boardChangeNotifier that caused the update
     * */
    private void updateTopBar(boardChangeNotifier notifier){
        if (!notifier.isGameOver()) {

            Object[] topElements =  this.topPane.getChildren().toArray();

            for(int i = 0; i < topElements.length; i++) {
                if(!((Node) topElements[i]).isVisible()) {
                    ((Node) topElements[i]).setVisible(true);
                }
                if (((Node) topElements[i]).getId() == "winner")
                    ((Node) topElements[i]).setVisible(false);
            }

            String type1 = this.controller.getPlayer1().playerType;
            String type2 = this.controller.getPlayer2().playerType;

            this.player1.setText(notifier.getPlayerCount('X') +
                    " ( "+ type1.substring(0, 1).toUpperCase() + type1.substring(1, type1.length()) + " )");

            this.player2.setText(notifier.getPlayerCount('O') +
                    " ( " + type2.substring(0,1).toUpperCase() + type2.substring(1, type2.length()) + " )");

        }
        else{

            Object winnerImage = null;
            boolean isDraw = false;

            if(this.controller.winner() == 'X') { winnerImage = new ImageView(OthelloApplication.blackToken); }
            else if(this.controller.winner() == 'O') {
                winnerImage = new Circle(14);
                ((Circle) winnerImage).setFill(Color.LIGHTGRAY);
                ((Circle) winnerImage).setStroke(Color.BLACK);
            }
            else { isDraw = true; }

            Alert winnerAlert;

            if(isDraw) {
                winnerAlert = new Alert(Alert.AlertType.INFORMATION, "" + " It's a draw");
                winnerAlert.setTitle("Game Over");
            }
            else {
                winnerAlert = new Alert(Alert.AlertType.INFORMATION);
                winnerAlert.setHeaderText("Winner");
                winnerAlert.setTitle("Game Over");
                if(this.controller.winner() == 'O') { winnerAlert.setGraphic((Circle) winnerImage); }
                else { winnerAlert.setGraphic((ImageView) winnerImage); }
            }

            winnerAlert.showAndWait();
        }
    }

    /**
     * Update the right flow pane
     *
     * @param notifier boardChangeNotifier that causes the update
     * */
    private void updateRightPane(boardChangeNotifier notifier) {

        if (notifier.isRestartRequest() || notifier.isGameOver()){
            this.p1Timer.resetTimer();
            this.p2Timer.resetTimer();
            this.moveCount = 0;
        }

        if(notifier.isRestartRequest()) {
            this.moveCount = 0;
            this.startTimer();
            notifier.restartRequestFulfilled();
        }

        char turn = notifier.getWhosTurn();

        this.turn.setImage(turn == 'X' ? OthelloApplication.blackToken : OthelloApplication.whiteToken);
        this.updateTime(this.p1Timer.getRemainingTime()[0], this.p1Timer.getRemainingTime()[1], 'X');
        this.updateTime(this.p2Timer.getRemainingTime()[0], this.p2Timer.getRemainingTime()[1], 'O');
    }

    /**
     * highlights on the board a random hint with a yellow circle
     * */
    private void hintMove(Move choice) {
        int[] coords = null;
        Object[] buttons = this.uiGrid.getChildren().toArray();
        // search for the button corresponding to this move
        for (Object button : buttons) {
            Button b = (Button) button;
            coords = UserInputHandler.parseID(b.getId());
            // update color and break inner loop
            if (choice.equals(coords)) {
                Circle hintOutlineCircle = new Circle(14);
                hintOutlineCircle.setFill(Color.TRANSPARENT);
                hintOutlineCircle.setStroke(Color.rgb(200, 225, 53));
//                b.setStyle("-fx-background-color: #ffff00");
                b.setGraphic(hintOutlineCircle);
                break;
            }
        }
    }

    /**
     * highlights on the board the greediest move possible with a red circle
     * */
    private void highlightGreedyHint(Move choice) {
        int[] coords = null;
        Object[] buttons = this.uiGrid.getChildren().toArray();
        // search for the button corresponding to this move
        for (Object button : buttons) {
            Button b = (Button) button;
            coords = UserInputHandler.parseID(b.getId());

            // update color and break inner loop
            if (choice.equals(coords)) {
                Circle hintOutlineCircle = new Circle(14);
                hintOutlineCircle.setFill(Color.TRANSPARENT);
                hintOutlineCircle.setStroke(Color.rgb(200, 25, 53));
//                b.setStyle("-fx-background-color: #ffa500");
                b.setGraphic(hintOutlineCircle);
                break;
            }
        }
    }

    /**
     * Switches between the p1Timer & p2Timer depending on whose turn it is.
     *
     * @param notifier
     * */
    private void switchActiveTimer(boardChangeNotifier notifier) {
        if(this.moveCount < notifier.getNumMoves() && notifier.getWhosTurn() == 'X') {
            this.p2Timer.pauseTimer();
            this.p1Timer.startTimer();
            this.moveCount++;
        }
        else if(this.moveCount < notifier.getNumMoves() && notifier.getWhosTurn() == 'O') {
            this.p1Timer.pauseTimer();
            this.p2Timer.startTimer();
            this.moveCount++;
        }
    }

    /**
     * Process the timer changes and update the GUI. The update task is queued using
     * runLater to ensure synchronous updates to GUI by different threads
     *
     * @param o Observable object that fired the update. It is of type CountdownTimer
     * */
    private void processTimerUpdate(Observable o) {
        CountdownTimer timeUpdater = (CountdownTimer) o;
        int[] timeRem = timeUpdater.getRemainingTime();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(timeUpdater.getLabel() == 'X') { updateTime(timeRem[0], timeRem[1], 'X'); }
                else { updateTime(timeRem[0], timeRem[1], 'O'); }
            }
        });
    }

    /**
     * Process the model updates and update the GUI accordingly. The update task is queued using
     * runLater to ensure synchronous updates to GUI by different threads
     *
     * @param o Observable object that fired the update. It is of type boardChangeNotifier
     * */
    private void processModelUpdate(Observable o) {
        boardChangeNotifier notifier = (boardChangeNotifier) o;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Object[] buttons = uiGrid.getChildren().toArray();

                updateGridButtons(notifier, buttons);

                if(!notifier.isGameOver()) { highlightValidMoves(notifier, buttons); }
                updateTopBar(notifier);
                updateRightPane(notifier);

                if (notifier.isHintRequired()){
                    if (notifier.getHint().getType().equals("random")){ hintMove(notifier.getHint().getHintMove()); }
                    else { highlightGreedyHint(notifier.getHint().getHintMove()); }
                }

                // move has been made, reset the timer. Timer is switched after every move only if
                // both players are humans.
                if(!controller.getPlayer1().isCPU()
                        && !controller.getPlayer2().isCPU()) {
                    switchActiveTimer(notifier);
                }
                else {
                    if(controller.getPlayer1().isCPU()) {
                        if(!p2Timer.isRunning()) {
                            p2Timer.startTimer();
                            p1Timer.pauseTimer();
                        }
                    }
                    else {
                        if(!p1Timer.isRunning()) {
                            p1Timer.startTimer();
                            p2Timer.pauseTimer();
                        }
                    }
                }

            }
        });
    }

    @Override
    public void update(Observable o) {
        if(o instanceof boardChangeNotifier) {
            // model is updating the GUI
            this.processModelUpdate(o);
        }
        else {
            // timer is updating the GUI with the time remaining
            this.processTimerUpdate(o);
        }
    }

}