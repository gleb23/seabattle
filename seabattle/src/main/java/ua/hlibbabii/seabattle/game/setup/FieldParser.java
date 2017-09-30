package ua.hlibbabii.seabattle.game.setup;

import ua.hlibbabii.seabattle.game.domain.Field;
import ua.hlibbabii.seabattle.game.domain.FieldSnapshot;
import ua.hlibbabii.seabattle.game.domain.Point;
import ua.hlibbabii.seabattle.game.domain.ship.Ship;
import ua.hlibbabii.seabattle.game.domain.ship.ShipOrientation;

/**
 * Created by hlib on 11.09.16.
 */
public class FieldParser extends FieldPopulator {

    public FieldParser(int dimensions) {
        super(dimensions);
    }

    public Field parseShips(FieldSnapshot shipSchema) {
        // add null and emptiness check here
        for (int i = 0; i < shipSchema.getRows(); i++) {
            for (int j = 0; j < shipSchema.getColumns(); j++) {
                if (shipSchema.isShipPresent(Point.create(i, j)) && getShip(i, j) == null) {
                    Ship ship = getShipFromSchema(shipSchema, i, j);
                    setShip(ship);
                }
            }
        }
        return new Field(cells, ships);
    }

    private Ship getShipFromSchema(FieldSnapshot shipSchema, int hor, int ver) {
        Point head = Point.create(hor, ver);
        int nHor = shipSchema.getRows();
        int nVer = shipSchema.getColumns();

        if (!shipSchema.isShipPresent(head)) {
            throw new IllegalArgumentException(String.format("Cell [%d, %d] is supposed to contain the head of the ship", hor, ver));
        }
        ShipOrientation shipOrientation = (hor + 1 < nHor && shipSchema.isShipPresent(Point.create(hor + 1, ver))) ?
                ShipOrientation.VERTICAL :
                ShipOrientation.HORIZONTAL;

        int nDecks = 1;
        if (shipOrientation == ShipOrientation.VERTICAL) {
            ++hor;
            while (hor < nHor && shipSchema.isShipPresent(Point.create(hor, ver))) {
                ++nDecks;
                ++hor;
            }
        } else {
            ++ver;
            while (ver < nVer && shipSchema.isShipPresent(Point.create(hor, ver))) {
                ++nDecks;
                ++ver;
            }
        }
        return Ship.createShip(head, shipOrientation, nDecks);
    }
}
