package ua.hlibbabii.seabattle.game.setup;

import java.util.Random;

import ua.hlibbabii.seabattle.game.domain.Cell;
import ua.hlibbabii.seabattle.game.domain.Point;
import ua.hlibbabii.seabattle.game.domain.Field;
import ua.hlibbabii.seabattle.game.domain.ship.Ship;
import ua.hlibbabii.seabattle.game.domain.ship.ShipOrientation;

/**
 * Created by hlib on 12.09.16.
 */
public class RandomFieldPopulator extends FieldPopulator {

    private static final Random rand = new Random();

    public RandomFieldPopulator(int dimensions) {
        super(dimensions);
    }

    public Field setShipsByRandom(int[] maxNumberOfShips) throws ShipCanNotBePlacedException {
        /*
         * The max number of attempts to place one ship
         */
        int dimensions = cells.length;
        int maxNumOfAttempts =
                dimensions * dimensions * dimensions * dimensions;

        int maxDeckNumber = maxNumberOfShips.length;
        for (int deckNumber = maxDeckNumber; deckNumber > 0; deckNumber--) {
            int numberOfShipsToPlace = maxNumberOfShips[deckNumber - 1];

            while (numberOfShipsToPlace > 0) {
                /*
                 * looping until the appropriate cell is found
                 */
                for (int attemptToPlace = 0; true; attemptToPlace++) {
                    /**
                     * If the number of attempts to place the ship exceeds max
                     * acceptable
                     */
                    if (attemptToPlace > maxNumOfAttempts) {
                        throw new ShipCanNotBePlacedException("Current state of fieldSnapshot:\n" + new Field(cells, ships));
                    }
                    Ship randomShip = createRandomShip(deckNumber);

                    if (!intersectsWithOtherShip(randomShip)) {
                        setShip(randomShip);
                        --numberOfShipsToPlace;
                        break;
                    }
                }
            }
        }
        return new Field(cells, ships);
    }

    private Ship createRandomShip(int deckNumber) {
        ShipOrientation shipOrientation = getRandomShipOrientation();
        Point point;
        if (shipOrientation == ShipOrientation.VERTICAL) {
            point = Point.getRandom(0, cells.length - deckNumber + 1, 0, cells.length);
        } else {
            point = Point.getRandom(0, cells.length, 0, cells.length - deckNumber + 1);
        }
        return Ship.createShip(point, shipOrientation, deckNumber);
    }

    private ShipOrientation getRandomShipOrientation() {
        return rand.nextBoolean() ? ShipOrientation.HORIZONTAL : ShipOrientation.VERTICAL;
    }
}
