package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.OthelloController;

/**
 * Restart command inherits from the generic ModelUpdateCommand and resets the model
 * */
public class RestartCommand extends ModelUpdateCommand{

    public RestartCommand(OthelloController controller) {
        super(controller);
    }

    @Override
    public void execute() {
        this.controller.resetBoard();
        this.controller.getGameBoard().forceUpdate();
    }
}
