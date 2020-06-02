package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.*;

/**
 * PlayerChangeCommand inherits from the generic ModelUpdateCommand to change Players playing the game
 * on the fly during the game
 * */
public class PlayerChangeCommand extends ModelUpdateCommand{

    String command;

    /**
     * Build a new player change command that will change one of the player type in the game
     *
     * @param controller the controller which manages the players and actually switches the players in the game
     * @param command the type of player to switch to ('human' or 'greedy' or 'random' or 'strategic')
     * */
    public PlayerChangeCommand(OthelloController controller, String command) {
        super(controller);
        this.command = command;
    }

    @Override
    public void execute() {
        this.controller.changePlayer(PlayerFactory.create(command, this.controller.getGameBoard().getWhosTurn(),
                this.controller));
    }
}
