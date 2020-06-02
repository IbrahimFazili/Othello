package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.OthelloController;

/**
 * MoveCommand to make a move on the board. This command inherits from the generic ModelUpdateCommand
 * */
public class MoveCommand extends ModelUpdateCommand {

    int x, y;

    /**
     * Create a new move command with coordinates where the move should be made
     *
     * @param controller the main OthelloController that updates the model
     * @param x x-coordinate for this move
     * @param y y-coordinate for this move
     * */
    public MoveCommand(OthelloController controller, int x, int y) {
        super(controller);
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
        // capture game state before making changes
        this.controller.getGameBoard().captureState();

        if(!this.controller.getGameBoard().move(x, y)) {
            // unsuccessful move, no need to save changes as no changes were made
            this.controller.getGameBoard().deleteLastState();
        }
    }
}
