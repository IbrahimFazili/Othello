package ca.utoronto.utm.othello.model;

import ca.utoronto.utm.othello.viewcontroller.UserInputHandler;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;


public abstract class OthelloController implements Observer {

	protected Othello othello;
	Player player1, player2;

	/**
	 * Constructs a new OthelloController with a new Othello game, ready to play
	 * with a user at the console.
	 */
	public OthelloController(Othello othello) {
		this.othello = othello;
//		this.report();
	}

	public Othello getGameBoard() { return this.othello; }

	public void resetBoard(){
		this.othello.restart();
		this.getGameBoard().updateNotifier.requestRestart();
	}

	public void changePlayer(Player player) {
		if(this.player1.playerType != "human") {
			player.player = this.player1.player;
			this.player1 = player;
		}
		else if(this.player2.playerType != "human") {
			player.player = this.player2.player;
			this.player2 = player;
		}
		else if(this.othello.getWhosTurn() == OthelloBoard.P1) { this.player1 = player; }
		else if(this.othello.getWhosTurn() == OthelloBoard.P2) { this.player2 = player; }
	}

	public void play() {
		while (!othello.isGameOver()) {
			this.report();
			
			Move move=null;
			char whosTurn = othello.getWhosTurn();
			
			if(whosTurn==OthelloBoard.P1)move = player1.getMove();
			if(whosTurn==OthelloBoard.P2)move = player2.getMove();

//			this.reportMove(whosTurn, move);
			othello.move(move.getRow(), move.getCol());
		}
//		this.reportFinal();
	}

	public Player getPlayer1(){
		return this.player1;
	}

	public Player getPlayer2(){
		return this.player2;
	}

	public char winner(){
		return  this.othello.getWinner();
	}

	/**
	 * creates a greedy hint Move using the getMove function
	 * from PlayerGreedy and sets it to the greedyHint
	 * in Othello
	 * */
	public void setGreedyHint() {
		PlayerGreedy hintGenerator = new PlayerGreedy(this.othello.copy(), this.othello.getWhosTurn());
		Move greedyHint = hintGenerator.getMove();
		this.othello.setGreedyHint(new Hint(greedyHint, "greedy"));
	}

	/**
	 * creates a random hint Move using the getMove function
	 * from PlayerRandom and sets it to the randomHint
	 * in Othello
	 * */
	public void setRandomHint() {
		PlayerRandom hintGenerator = new PlayerRandom(this.othello.copy(), this.othello.getWhosTurn());
		Move randomHint = hintGenerator.getMove();
		this.othello.setRandomHint(new Hint(randomHint, "random"));
	}

	/**
	 * Check if the current turn is of player controlled by the CPU
	 *
	 * @return boolean if its CPU's Turn
	 * */
	public boolean isCPUturn() {
		if(this.othello.getWhosTurn() == this.player1.getPlayer()) {
			return this.player1.isCPU();
		}
		if(this.othello.getWhosTurn() == this.player2.getPlayer()) {
			return this.player2.isCPU();
		}

		return false;
	}

	/**
	 * Update the model when we receive an update from the GUI for user input
	 *
	 * @param o The GUI move input object that updates based on user's input and
	 *          notifies this model of the update
	 * */
	@Override
	public void update(Observable o) {
		// execute all commands issued by the handler
		((UserInputHandler) o).executeAll();

	}

	protected void reportMove(char whosTurn, Move move) { }
	protected void report() { }
	protected void reportFinal() { }
}
