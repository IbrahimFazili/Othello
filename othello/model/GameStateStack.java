package ca.utoronto.utm.othello.model;

import java.util.ArrayList;

/**
 * Stack to keep track of multiple game states in order.
 * */
public class GameStateStack {

    private ArrayList<GameStateCapture> stack;

    public GameStateStack() { this.stack = new ArrayList<>(); }

    public boolean isEmpty() { return this.stack.size() == 0; }

    /**
     * Saves the game state to the stack
     *
     * @param state GameCaptureState object that captured the game state
     * */
    public void push(GameStateCapture state) { this.stack.add(state); }

    /**
     * @return pop the topmost GameStateStack
     * */
    public GameStateCapture pop() {
        if(this.isEmpty()) { return null; }
        return this.stack.remove(this.stack.size() - 1);
    }

    /**
     * Return copy of the topmost GameStateCapture
     *
     * @return GameStateCapture of the most recent saved game state
     * */
    public GameStateCapture peek() {
        if(this.stack.isEmpty()){
            return null;
        }
        return this.stack.get(this.stack.size() - 1).copy();
    }

    /**
     * clears the stack
     * */
    public void clearStack() { this.stack.clear(); }
}
