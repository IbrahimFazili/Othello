package ca.utoronto.utm.othello.model;

public class OthelloControllerHumanVsAny extends OthelloControllerVerbose {
    /**
     * Constructs a new OthelloController with a new Othello game, ready to play
     * with a user and Player Human, Player Random or Player Greedy
     */
    public OthelloControllerHumanVsAny() {
        this.player1 = new PlayerHuman(this.othello, OthelloBoard.P1);
        this.player2 = new PlayerHuman(this.othello, OthelloBoard.P2);
    }

}
