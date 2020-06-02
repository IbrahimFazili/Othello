package ca.utoronto.utm.othello.model;

/**
 * Hint class, describes what kind of hint the user wants
 * consists of the Move and String which indicates
 * what type of hint it is, either random or greedy
 * */
public class Hint {
    Move hintMove;
    String type;
    public Hint(Move hintMove, String type) {
        this.hintMove = hintMove;
        this.type = type;
    }

    public String getType() { return this.type; }
    public Move getHintMove() { return this.hintMove; }
}
