package ca.utoronto.utm.othello.model;

import java.util.ArrayList;
import java.util.Random;
/**
 * PlayerStrategic is another kind of Player
 * Smartest CPU the player can play
 * 4 kinds of moves it can make in order of preference,
 * corner move, side move, inner square move and the final
 * is any general max move
 * */
public class PlayerStrategic extends Player {

    public PlayerStrategic(Othello othello, char player) {
        super(othello, player, "strategy");
    }
    @Override

    public Move getMove() {
        ArrayList<Move> possibleMoves = this.othello.possibleMoves();
        if (this.cornerMoves(possibleMoves) != null)
            return this.cornerMoves(possibleMoves);
        else if (this.sideMoves(possibleMoves) != null)
            return this.sideMoves(possibleMoves);
        else if (this.greedyFourByFour(possibleMoves) != null)
            return this.greedyFourByFour(possibleMoves);
        return this.greedyMove();
    }
    /**
     * first choice move for the strategic player
     * looks at all the corners and finds the available moves
     * if theres multiple, makes a copy and finds the best one
     *
     * @param possibleMoves an arraylist of possible moves
     * @return best corner Move
     */
    private Move cornerMoves(ArrayList<Move> possibleMoves){
        //to find the corner move
        ArrayList<Move> cornerMoves = new ArrayList<Move>();
        for (Move cornerMove : possibleMoves){
            if (cornerMove.toString().equals("(0,0)") || cornerMove.toString().equals("(0,7)")
                    || cornerMove.toString().equals("(7,0)") || cornerMove.toString().equals("7,7")){
                cornerMoves.add(cornerMove);
            }
        }
        if (cornerMoves.size() > 1){
//            int pos = choice.nextInt(cornerMoves.size());
//            return cornerMoves.get(pos);
            Othello copy;
            int max = 0;
            Move best = null;
            for (int i = 0; i <= cornerMoves.size()-1; i++){
                copy = this.othello.copy();
                copy.move(cornerMoves.get(i).getRow(),
                        cornerMoves.get(i).getRow());
                if (copy.getCount(this.player) > max){
                    best = cornerMoves.get(i);
                    max = copy.getCount(this.player);
                }
            }
            return best;
        }
        // executes when the size equal 1
        else if (cornerMoves.size() == 1)
            return cornerMoves.get(0);
        return null;
    }
    /**
     * second choice move for the strategic player
     * looks at all the sides and finds the available moves
     * if theres multiple, makes a copy and finds the best one
     *
     * @param possibleMoves an arraylist of possible moves
     * @return best side Move
     */
    private Move sideMoves(ArrayList<Move> possibleMoves){
        ArrayList<Move> sideMoves = new ArrayList<Move>();
        for (Move sideMove : possibleMoves) {
            if (sideMove.toString().substring(1, 2).equals("0") ||
                    sideMove.toString().substring(3, 4).equals("0")) {
                sideMoves.add(sideMove);
            }
        }
        if (sideMoves.size() > 1){
            Othello copy;
            int max = 0;
            Move best = null;
            for (int i = 0; i <= sideMoves.size()-1; i ++){
                copy = this.othello.copy();
                copy.move(sideMoves.get(i).getRow(),
                        sideMoves.get(i).getRow());
                if (copy.getCount(this.player) > max){
                    best = sideMoves.get(i);
                    max = copy.getCount(this.player);
                }
            }
            return best;
            }
        else if (sideMoves.size() == 1)
            return sideMoves.get(0);
        return null;
    }
    /**
     * third choice move for the strategic player
     * looks at all the points in 4 x 4 of the center
     * of the board and finds the available moves
     * if theres multiple, makes a copy and finds the best one
     *
     * @param possibleMoves an arraylist of possible moves
     * @return best inner square Move
     */
    private Move greedyFourByFour(ArrayList<Move> possibleMoves){
        Move bestFourMove = null;
        int max = 0;
        Othello copy;
        for (Move fourByFour: possibleMoves){
            if (fourByFour.getRow() >= 2 && fourByFour.getRow() <= 5
                    && fourByFour.getCol() >= 2 && fourByFour.getCol() <= 5){
                copy = this.othello.copy();
                if (copy.move(fourByFour.getRow(), fourByFour.getCol()) && copy.getCount(this.player)>max){
                    bestFourMove = fourByFour;
                    max = copy.getCount(this.player);
                }
            }
        }
        if (bestFourMove != null){
            return bestFourMove;
        }
        return null;
    }
    /**
     * last choice move for the strategic player
     * looks at all the points of the board and finds the available moves
     * if theres multiple, makes a copy and finds the best one
     *
     * @return best inner square Move
     */
    private Move greedyMove(){
        Othello othelloCopy = othello.copy();
        Move bestMove=new Move(0,0);
        int bestMoveCount=othelloCopy.getCount(this.player);;
        for(int row=0;row<Othello.DIMENSION;row++) {
            for(int col=0;col<Othello.DIMENSION;col++) {
                othelloCopy = othello.copy();
                if(othelloCopy.move(row, col) && othelloCopy.getCount(this.player)>bestMoveCount) {
                    bestMoveCount = othelloCopy.getCount(this.player);
                    bestMove = new Move(row,col);
                }
            }
        }
        return bestMove;
    }
}
