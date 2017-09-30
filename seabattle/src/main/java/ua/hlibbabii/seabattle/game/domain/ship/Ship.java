package ua.hlibbabii.seabattle.game.domain.ship;

import java.io.Serializable;

import ua.hlibbabii.seabattle.game.domain.Point;

/**
 * Created by hlib on 04.09.16.
 */
public class Ship {

    public Point getHead() {
        return head;
    }

    public ShipOrientation getShipOrientation() {
        return shipOrientation;
    }

    /**
     * Thrown when attempt to kill already killed ship occured ???
     */
    public static class ShipAlreadyKilledException extends RuntimeException {

        public ShipAlreadyKilledException() {
        }
    }

    /** The number of decks in the ship*/
    private int nDecks;

    /** The number of left decks, if 0 - the ship is killed*/
    private int nDecksLeft;

    private Point head;

    private ShipOrientation shipOrientation;

    private Ship(Point head, ShipOrientation shipOrientation, int nDecks, int nDecksLeft ) {
        this.nDecks = nDecks;
        this.nDecksLeft = nDecksLeft;
        this.head = head;
        this.shipOrientation = shipOrientation;
    }

    public static Ship createShip(Point head, ShipOrientation shipOrientation, int nDecks) {
        return new Ship(head, shipOrientation, nDecks, nDecks);
    }

    /**
     * returns the number of decks at all
     * @return the number of decks at all
     */
    public int getNDecks() {
        return nDecks;
    }

    /**
     * returns the number of decks that left
     * @return the number of decks that left
     */
    public int getNDecksLeft() {
        return nDecksLeft;
    }

    public boolean isKilled() {
        return getNDecksLeft() <= 0;
    }

    /**
     * Kills one deck of the ship by reducing the number of left decks
     * @throws Ship.ShipAlreadyKilledException if ship
     * already killed (0 decks left).
     */
    public void killOneDeck() {
        if (isKilled()) {
            throw new ShipAlreadyKilledException();
        }
        this.nDecksLeft--;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "nDecks=" + nDecks +
                ", nDecksLeft=" + nDecksLeft +
                ", head=" + head +
                ", shipOrientation=" + shipOrientation +
                '}';
    }
}
