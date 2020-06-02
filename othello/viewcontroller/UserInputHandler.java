package ca.utoronto.utm.othello.viewcontroller;


import ca.utoronto.utm.othello.model.OthelloController;
import ca.utoronto.utm.util.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.ArrayList;

/**
 * Input handler class to handle all the Inputs on the GUI.
 * This is the part of the controller for the application. It is fired when a button is clicked on the GUI
 * and builds the appropriate command to update the model using CommandFactory. The generated command
 * is added to the commands list and the model is notified of the unexecuted command which is then executed.
 * The executed command actually updates the model.
 * */
public class UserInputHandler extends Observable implements EventHandler<ActionEvent> {

    private OthelloController controller;
    private ArrayList<ModelUpdateCommand> commands;

    /**
     * Initialize the input handler to handle user clicks on
     * button grid
     *
     * @param controller OthelloController for the game
     * */
    public UserInputHandler(OthelloController controller) {
        this.controller = controller;
        this.commands = new ArrayList<>();
    }

    /**
     * Parse the button id to get the coordinate of the button on the grid
     *
     * @param id the id of the button
     * @return int[] of coordinates of format [x, y]
     * */
    public static int[] parseID(String id) {
        int x = Integer.parseInt(id.substring(0, 1));
        int y = Integer.parseInt(id.substring(2, 3));
        int[] retArr = {x, y};
        return retArr;
    }

    /**
     * Add a new command to commands list which will be executed by the controller.
     * After adding this command, the observers are notified
     *
     * @param command ModelUpdateCommand which gets added to the commands list.
     * */
    private void addCommand(ModelUpdateCommand command) {
        this.commands.add(command);
        this.notifyObservers();
    }

    /**
     * Execute all the commands in the commands ArrayList
     * */
    public void executeAll() {
        while(this.commands.size() > 0) {
            this.commands.get(0).execute();
            this.commands.remove(0);
        }
    }

    /**
     * @param event click ActionEvent fired when user clicks any button on
     *              the 8x8 grid
     * */
    public void handle(ActionEvent event) {
        Button clicked = (Button) event.getSource();

        ModelUpdateCommand command = CommandFactory.create(clicked.getStyleClass().toString(),
                this.controller, clicked.getId());

        this.addCommand(command);

        if(this.controller.isCPUturn()) {
            this.addCommand(CommandFactory.create("CPUturn", this.controller, ""));
        }
    }

}
