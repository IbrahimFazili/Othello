package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Move;
import ca.utoronto.utm.othello.model.OthelloController;

/**
 * Command to make a CPU move on the board. Inherits from the generic ModelUpdateCommand
 * */
public class CPUTurnCommand extends ModelUpdateCommand {

    public CPUTurnCommand(OthelloController controller) {
        super(controller);
    }

    @Override
    public void execute() {
        Move move = this.controller.getGameBoard().getWhosTurn() == this.controller.getPlayer1().getPlayer() ?
                this.controller.getPlayer1().getMove() : this.controller.getPlayer2().getMove();

        this.controller.getGameBoard().move(move.getRow(), move.getCol());
    }
}
