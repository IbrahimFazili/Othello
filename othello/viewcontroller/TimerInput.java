package ca.utoronto.utm.othello.viewcontroller;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.Scanner;
/**
 * Input handler for the Timer Alert, where a user is allowed to enter the time
 * they want each player to have to complete the game
 * executed at the beginning of the application and passes the received values
 * to the GUIUpdater class
 * */
public class TimerInput {
        private BorderPane pane;
        private TextField playerOneMinutes;
        private TextField playerOneSeconds;
        private TextField playerTwoMinutes;
        private TextField playerTwoSeconds;
        private Alert alert;
        private GuiUpdater updater;

        public TimerInput(GuiUpdater updater){
            this.pane = new BorderPane();
            this.alert = new Alert(Alert.AlertType.INFORMATION);
            this.playerOneMinutes = new TextField();
            this.playerOneMinutes.setPromptText("minutes");
            this.playerOneSeconds = new TextField();
            this.playerOneSeconds.setPromptText("seconds");
            this.playerTwoMinutes = new TextField();
            this.playerTwoMinutes.setPromptText("minutes");
            this.playerTwoSeconds = new TextField();
            this.playerTwoSeconds.setPromptText("seconds");
            this.pane.setTop(new Label("Enter the allocated time for each player"));
            this.alert.setTitle("Timer");
            this.alert.setHeaderText(null);

            this.updater = updater;
        }

        public void AskTheTime(){
            HBox messagePlayerOne = new HBox();
            HBox messagePlayerTwo = new HBox();
            messagePlayerOne.getChildren().addAll(new Label("Player 1: "),
                    this.playerOneMinutes, this.playerOneSeconds);
            messagePlayerTwo.getChildren().addAll(new Label("Player 2: "),
                    this.playerTwoMinutes, this.playerTwoSeconds);
            this.pane.setCenter(messagePlayerOne);
            this.pane.setBottom(messagePlayerTwo);
            this.alert.setGraphic(this.pane);
            this.alert.getDialogPane().setMaxSize(500, 580);
            this.alert.showAndWait();

            int[] totalTime = new int[4];

            totalTime[0] = !this.playerOneMinutes.getText().equals("") ?
                    Integer.parseInt(this.playerOneMinutes.getText()) : 5;
            totalTime[1] = !this.playerOneSeconds.getText().equals("") ?
                    Integer.parseInt(this.playerOneSeconds.getText()) : 0;
            totalTime[2] = !this.playerTwoMinutes.getText().equals("") ?
                    Integer.parseInt(this.playerTwoMinutes.getText()) : 5;
            totalTime[3] = !this.playerTwoSeconds.getText().equals("") ?
                    Integer.parseInt(this.playerTwoSeconds.getText()) : 0;


            this.updater.setStartingTime(totalTime);
        }
}
