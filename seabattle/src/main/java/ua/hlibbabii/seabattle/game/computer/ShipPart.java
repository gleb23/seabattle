package ua.hlibbabii.seabattle.game.computer;

import java.util.Optional;

import ua.hlibbabii.seabattle.game.domain.Point;
import ua.hlibbabii.seabattle.game.domain.ship.ShipOrientation;


/**
 * Created by hlib on 14.10.16.
 */
public class ShipPart {
    private Point currentHead;
    private int currentDecksNumber;
    private ShipOrientation shipOrientation;

    private ShipPart(Point currentHead, int currentDecksNumber, ShipOrientation shipOrientation) {
        this.currentHead = currentHead;
        this.currentDecksNumber = currentDecksNumber;
        this.shipOrientation = shipOrientation;
    }

    public static ShipPart createFromPoint(Point point) {
       return new ShipPart(point, 1, null);
    }

    public ShipPart incrementUp() {
        return new ShipPart(currentHead.decreaseHor(), currentDecksNumber + 1,
                ShipOrientation.VERTICAL);
    }

    public ShipPart incrementDown() {
        return new ShipPart(currentHead, currentDecksNumber + 1,
                ShipOrientation.VERTICAL);
    }

    public ShipPart incrementLeft() {
        return new ShipPart(currentHead.decreaseVer(), currentDecksNumber + 1,
                ShipOrientation.HORIZONTAL);
    }

    public ShipPart incrementRight() {
        return new ShipPart(currentHead, currentDecksNumber + 1,
                ShipOrientation.HORIZONTAL);
    }

    public int getCurrentDecksNumber() {
        return currentDecksNumber;
    }

    public Point getCurrentHead() {
        return currentHead;
    }

    public ShipOrientation getShipOrientation() {
        return shipOrientation;
    }
}
