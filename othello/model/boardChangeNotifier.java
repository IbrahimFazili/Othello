package ca.utoronto.utm.othello.model;

import ca.utoronto.utm.util.Observable;

import java.util.ArrayList;

public class boardChangeNotifier extends Observable {

    private OthelloBoard gameBoard;
    private Othello game;
    private ArrayList<Move> availMoves;
    private boolean restartRequest = false;
    private Hint hint;
    private boolean isGameOver;
    private boolean isHintRequired;

    boardChangeNotifier(Othello game) {
        this.gameBoard = game.getBoard();
        this.game = game;
        this.isGameOver = this.game.isGameOver();
    }

    // accessors for private attributes
    public OthelloBoard getBoard() { return this.gameBoard; }
    public int getPlayerCount(char player) { return this.game.getCount(player); }
    public char winner(){return this.game.getWinner();}
    public char getWhosTurn() { return this.game.getWhosTurn(); }
    public int getNumMoves() { return this.game.getNumMoves(); }
    public ArrayList<Move> getAvailMoves() { return this.availMoves; }
    public boolean isGameOver() { return this.isGameOver; }
    public boolean isRestartRequest() { return this.restartRequest; }
    public void restartRequestFulfilled(){ this.restartRequest = false;}
    // hint feature
    public Hint getHint(){ return this.hint; }
    public boolean isHintRequired(){ return this.isHintRequired; }

    public GameStateCapture peekLastState() { return this.game.peekLastState(); }
    public void requestRestart() { this.restartRequest = true; }
    public void enableHint() { this.isHintRequired = true; }
    public void disableHint() {
        this.isHintRequired = false;
        this.hint = null;
    }

    public void setHint(Hint hint) { this.hint = hint; }

    public void updateAndNotify(OthelloBoard gameBoard) {
        // update attributes
        this.gameBoard = gameBoard;
        this.availMoves = this.game.possibleMoves();
        this.isGameOver = this.game.isGameOver();
        this.notifyObservers();
    }
}
