package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.OthelloController;

/**
 * Factory to generate commands based on the button clicked on the GUI
 * */
public class CommandFactory {

    /**
     * Build and return a ModelUpdateCommand based on the "command" provided. If no valid match,
     * returns null
     *
     * @param command the command to generate
     * @param controller OthelloController that the command's gonna use to modify the model
     * @param coords coordinate of the button on the grid. Need only for generating a move command
     * @return ModelUpdateCommand
     * */
    public static ModelUpdateCommand create(String command, OthelloController controller, String coords) {

        if(command.equals("button VS Human")) { return new PlayerChangeCommand(controller, "human"); }
        if(command.equals("button VS Greedy")){ return new PlayerChangeCommand(controller, "greedy"); }
        if(command.equals("button VS Random")) { return new PlayerChangeCommand(controller, "random"); }
        if(command.equals("button VS Strategic")){return new PlayerChangeCommand(controller, "strategic");}

        if(command.equals("button gridBtn")) {
            int[] id = UserInputHandler.parseID(coords);
            return new MoveCommand(controller, id[0], id[1]);
        }

        if(command.equals("button Restart")) { return new RestartCommand(controller); }

        if(command.equals("button Undo")) {
            return new UndoCommand(controller);
        }

        if(command.equals("CPUturn")) { return new CPUTurnCommand(controller); }

        if(command.equals("button Hint")){
            return new HintCommand(controller, "random");
        }

        else if(command.equals("button Greedy")){
            return new HintCommand(controller, "greedy");
        }

        return null;
    }
}
