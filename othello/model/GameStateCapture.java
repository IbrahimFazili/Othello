package ca.utoronto.utm.othello.model;

public class GameStateCapture {

    private OthelloBoard board;
    private char whosTurn;
    private int numMoves;

    /**
     * Capture the Othello game's state, i.e., all the
     * important attributes needed to recreate the game to
     * that stage
     *
     * @param game Othello game whose state is captured
     * */
    public GameStateCapture(Othello game) {
        this.board = game.getBoard().copy();
        this.whosTurn = game.getWhosTurn();
        this.numMoves = game.getNumMoves();
    }

    private GameStateCapture(OthelloBoard board, char whosTurn, int numMoves) {
        this.board = board;
        this.whosTurn = whosTurn;
        this.numMoves = numMoves;
    }

    public OthelloBoard getBoard() { return this.board; }
    public char getWhosTurn() { return this.whosTurn; }
    public int getNumMoves() { return this.numMoves; }
    public GameStateCapture copy() {
        return new GameStateCapture(this.board, this.whosTurn, this.numMoves);
    }
}
