package ca.utoronto.utm.othello.viewcontroller;
import ca.utoronto.utm.othello.model.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/**
 * Main Application class for the Othello game. This class sets up the GUI and initializes the model (Othello) and
 * and controllers, links them and launches the game
 * */
public class OthelloApplication extends Application {

	public final static String topPaneTextColor = "-fx-background-color: transparent; " +
			"-fx-text-inner-color: #030202;" + "-fx-font-family: Comic Sans MS;";

	public final static Image blackToken = new Image("bToken.png");
	public final static Image whiteToken = new Image("wToken.png", 28, 28,
			false, false);

	private FlowPane buildTopPane(UserInputHandler handler) {
		FlowPane topBoard = new FlowPane();
		topBoard.setPadding(new Insets(0, 30, 0, 70));

		HBox p1 = new HBox();
		HBox p2 = new HBox();

		ImageView blackTokenView = new ImageView(blackToken);
		ImageView whiteTokenView = new ImageView(whiteToken);

		TextField player1 = new TextField("2  ( Human )");
		player1.setFocusTraversable(false);
		player1.setEditable(false);

		TextField player2 = new TextField("2 ( Human )");
		player2.setEditable(false);
		player2.setFocusTraversable(false);

		Button undoButton = new Button("Undo");
		undoButton.getStyleClass().add("Undo");
		undoButton.setOnAction(handler);

		player1.setStyle(OthelloApplication.topPaneTextColor);
		player2.setStyle(OthelloApplication.topPaneTextColor);

		p1.getChildren().addAll(blackTokenView, player1);
		p2.getChildren().addAll(whiteTokenView, player2);

		topBoard.getChildren().addAll(p1, p2, undoButton);
		return topBoard;
	}

	GridPane buildButtonGrid(UserInputHandler handler) {
		GridPane grid = new GridPane();
		grid.setHgap(0);
		grid.setVgap(0);
		grid.setPadding(new Insets(20, 10, 10, 10));

		//create 64 buttons
		for (int x = 0; x < 8; x++){
			for (int y = 0; y < 8; y++){
				Button newButton = new Button();
				newButton.setPrefWidth(180);
				newButton.setPrefHeight(180);
				newButton.getStyleClass().add("gridBtn");
				newButton.getStylesheets().add("ca/utoronto/utm/othello/viewcontroller/main.css");
				newButton.setId( y + "," + x);
				newButton.setOnAction(handler);
				grid.add(newButton, x, y);
			}
		}
		return grid;
	}

	private FlowPane buildBottomPane(UserInputHandler handler) {

		FlowPane bottomPane = new FlowPane();

		Button humanVsHuman = new Button("VS Human");
		humanVsHuman.setOnAction(handler);
		humanVsHuman.getStyleClass().add("VS Human");

		Button humanVsGreedy = new Button("VS Greedy");
		humanVsGreedy.setOnAction(handler);
		humanVsGreedy.getStyleClass().add("VS Greedy");

		Button humanVsRandom = new Button("VS Random");
		humanVsRandom.setOnAction(handler);
		humanVsRandom.getStyleClass().add("VS Random");

		Button humanVsStrategic = new Button("VS Strategic");
		humanVsStrategic.setOnAction(handler);
		humanVsStrategic.getStyleClass().add("VS Strategic");

		bottomPane.setHgap(5);
		bottomPane.setVgap(5);

		TextField game_options = new TextField("Game Options: ");
		game_options.setPrefWidth(110);
		game_options.setFocusTraversable(false);
		game_options.setEditable(false);
		game_options.setStyle(this.topPaneTextColor);

		bottomPane.getChildren().addAll(game_options, humanVsHuman, humanVsGreedy, humanVsRandom,
				humanVsStrategic);

		return bottomPane;
	}

	private FlowPane buildRightPane(UserInputHandler handler) {
		FlowPane pane = new FlowPane();
		pane.setPrefWidth(20);
		pane.setPadding(new Insets(10, 10, 10, 10));

		TextField timerText = new TextField();
		TextField player2_timer = new TextField();
		timerText.setEditable(false);
		timerText.setFocusTraversable(false);
		player2_timer.setEditable(false);
		player2_timer.setFocusTraversable(false);

		HBox turnContainer = new HBox();
		turnContainer.setPrefWidth(80);

		TextField theTurn = new TextField("Turn");
		theTurn.setEditable(false);
		theTurn.setFocusTraversable(false);

		turnContainer.getChildren().addAll(theTurn, new ImageView(blackToken));

		theTurn.setStyle(OthelloApplication.topPaneTextColor);
		timerText.setStyle(OthelloApplication.topPaneTextColor);
		player2_timer.setStyle(OthelloApplication.topPaneTextColor);

		FlowPane hintBox = new FlowPane(Orientation.VERTICAL);
		hintBox.setVgap(10);

		Button hint = new Button("Random Hint");
		hint.getStyleClass().add("Hint");
		hint.setOnAction(handler);

		//makes a hint for greedy move
		Button greedyHint = new Button("Greedy   Hint");
		greedyHint.getStyleClass().add("Greedy");
		greedyHint.setOnAction(handler);

		Button restart = new Button("Restart");
		restart.getStyleClass().add("Restart");
		restart.setOnAction(handler);


		hintBox.getChildren().addAll(hint, greedyHint, restart);
		pane.setVgap(20);
		pane.getChildren().addAll(turnContainer, timerText, player2_timer, hintBox);

		return pane;
	}

	private void setupBoardPane(BorderPane pane, FlowPane topBoard, GridPane grid, FlowPane bottomPane,
								FlowPane rightPane) {

		pane.setPadding(new Insets(10, 5, 10, 5));

		Image backgroundImg = new Image("background.jpg");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false,
				false, true, true);
		Background background = new Background(new BackgroundImage(backgroundImg, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));

		// pane.setBackground(background);

		pane.setTop(topBoard);
		pane.setCenter(grid);
		pane.setBottom(bottomPane);
		pane.setRight(rightPane);

		pane.setStyle("-fx-background-color: #367d40");
	}

	@Override
	public void start(Stage stage) throws Exception {

		// Create and hook up the Model, View and the controller
		
		// MODEL
		OthelloControllerHumanVsAny controller = new OthelloControllerHumanVsAny();


		// CONTROLLER
		// CONTROLLER->MODEL hookup

		//handler goes here
		UserInputHandler handler = new UserInputHandler(controller);
		handler.attach(controller);

		// main button grid
		GridPane grid = this.buildButtonGrid(handler);

		FlowPane topBoard = this.buildTopPane(handler);

		FlowPane bottomPane = this.buildBottomPane(handler);

		FlowPane rightPane = this.buildRightPane(handler);

		// VIEW
		// VIEW->CONTROLLER hookup
		// main updater for the GUI. Receives Updates from model and updates GUI elements
		GuiUpdater updater = new GuiUpdater(grid, topBoard, rightPane, controller);

		// MODEL->VIEW hookup
		controller.getGameBoard().updateNotifier.attach(updater);

		// makes alert for asking time
		TimerInput askTime = new TimerInput(updater);
		askTime.AskTheTime();

		BorderPane mainBorderPane = new BorderPane();
		this.setupBoardPane(mainBorderPane, topBoard, grid, bottomPane, rightPane);

		Scene scene = new Scene(mainBorderPane, 780, 580);
		stage.setTitle("Othello");
		stage.setScene(scene);
		stage.setResizable(false);
		controller.getGameBoard().forceUpdate();

		// LAUNCH THE GUI
		stage.show();

		stage.setOnCloseRequest(event -> { updater.killTimers(); });

		updater.startTimer();
	}

	public static void main(String[] args) {
		OthelloApplication view = new OthelloApplication();
		launch(args);
	}
}
