package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.*;

/**
 * Factory to generate Player based on the type provided.
 * */
public class PlayerFactory {

    public static Player create(String type, char token, OthelloController controller) {

        if(type.equals("human")) { return new PlayerHuman(controller.getGameBoard(), token); }
        if(type.equals("greedy")) { return new PlayerGreedy(controller.getGameBoard(), token); }
        if(type.equals("random")) { return new PlayerRandom(controller.getGameBoard(), token); }
        if(type.equals("strategic")) { return new PlayerStrategic(controller.getGameBoard(), token); }

        return null;
    }
}
