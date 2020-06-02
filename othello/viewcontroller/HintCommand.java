package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.OthelloController;

/**
 * Command Class that is used to display the hints, user either chooses
 * a random or greedy hint
 * */
public class HintCommand extends ModelUpdateCommand{
    private String command;
    public HintCommand(OthelloController controller, String command) {
        super(controller);
        this.command = command;
    }

/**
 * acts as a toggle, if pressed once, updates the notifier to set it true and that
 * in turn updates the GUIUpdater
 * if pressed twice, disables random hint
 * */
    private void randomHintDisplay(){
        if(this.controller.getGameBoard().updateNotifier.getHint() != null &&
                this.controller.getGameBoard().updateNotifier.getHint().getType().equals("random")) {
            this.controller.getGameBoard().updateNotifier.disableHint();
        }
        else {
            this.controller.setRandomHint();
        }
    }
    /**
     * acts as a toggle, if pressed once, updates the notifier to set it true and that
     * in turn updates the GUIUpdater
     * if pressed twice, disables greedy hint
     * */
    private void greedyHintDisplay(){
        if(this.controller.getGameBoard().updateNotifier.getHint() != null &&
                this.controller.getGameBoard().updateNotifier.getHint().getType().equals("greedy")) {
            this.controller.getGameBoard().updateNotifier.disableHint();
        }
        else {
            this.controller.setGreedyHint();
        }
    }

    @Override
    public void execute() {
        if (this.command.equals("random")){
            this.randomHintDisplay();
            this.controller.getGameBoard().forceUpdate();
        }
        else {
            this.greedyHintDisplay();
            this.controller.getGameBoard().forceUpdate();
        }
    }
}
