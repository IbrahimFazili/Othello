package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.OthelloController;

/**
 * This a template for a generic command that makes some modification to the model
 * */
public abstract class ModelUpdateCommand {
    OthelloController controller;

    /**
     * Build and Initialize this Command
     * @param controller The controller that actually modifies the model
     * */
    public ModelUpdateCommand(OthelloController controller) { this.controller = controller; }

    /**
     * Execute this command and actually modify the model
     * */
    public abstract void execute();
}
