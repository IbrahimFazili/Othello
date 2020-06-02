package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.OthelloController;

/**
 * UndoCommand Inherits from the generic ModelUpdateCommand. This command reverts the model state to
 * the one before last update
 * */
public class UndoCommand extends ModelUpdateCommand {

    public UndoCommand(OthelloController controller) { super(controller); }

    @Override
    public void execute() { this.controller.getGameBoard().undo(); }
}
