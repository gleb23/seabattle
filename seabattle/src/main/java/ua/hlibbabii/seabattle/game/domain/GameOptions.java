package ua.hlibbabii.seabattle.game.domain;

import ua.hlibbabii.seabattle.game.domain.ship.ShipPlacementMethod;
import ua.hlibbabii.seabattle.game.setup.FirstTurnStrategy;

/**
 * Created by hlib on 12.10.16.
 */
public class GameOptions {
    /**
     * default number of dimensions that fields have
     */
    private int DEFAULT_DIMENSION = 10;
    private int MAX_NUMBER_OF_SHIPS[] = {4, 3, 2, 1};
    private ShipPlacementMethod player1placementMethod = ShipPlacementMethod.RANDOMLY;
    private ShipPlacementMethod player2placementMethod = ShipPlacementMethod.RANDOMLY;
    private FirstTurnStrategy firstTurnStrategy = FirstTurnStrategy.PLAYER_FIRST;

    public int getFieldDimension() {
        return DEFAULT_DIMENSION;
    }

    public int[] getMaxNumberOfShips() {
        return MAX_NUMBER_OF_SHIPS;
    }

    public FirstTurnStrategy getFirstTurnStrategy() {
        return firstTurnStrategy;
    }

    public void setFirstTurnStrategy(FirstTurnStrategy firstTurnStrategy) {
        this.firstTurnStrategy = firstTurnStrategy;
    }
}
