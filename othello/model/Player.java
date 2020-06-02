package ca.utoronto.utm.othello.model;

public abstract class Player {
	protected Othello othello;
	protected char player;
	public String playerType;

	public Player(Othello othello, char player, String playerType) {
		this.othello=othello;
		this.player=player;
		this.playerType = playerType;
	}
	public char getPlayer() {
		return this.player;
	}
	public abstract Move getMove();
	public boolean isCPU() {
		return this.playerType.equals("greedy") || this.playerType.equals("random")
				|| this.playerType.equals("strategy");
	}
}
