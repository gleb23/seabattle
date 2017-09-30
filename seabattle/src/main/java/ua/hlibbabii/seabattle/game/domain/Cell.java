package ua.hlibbabii.seabattle.game.domain;

import java.io.Serializable;

import ua.hlibbabii.seabattle.game.domain.ship.Ship;

/**
 * Created by hlib on 04.09.16.
 */
public class Cell {

    private Ship ship;

    /** true - if this cell is open, otherwise - false*/
    private boolean isOpen;

    /**
     * Defines a not open cell with no ships placed in
     */
    public Cell() {
        ship = null;
        isOpen = false;
    }

    /**
     * return information whether this cell is open or not
     *
     * @return true - if this cell is open, otherwise - false
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Set the state of the cell depending on the value of f1
     * @param state - if true - cell sets open, otherwise - not open
     */
    public void open(boolean state) {
        isOpen = state;
    }

    /**
     * Sets a ship that is placed in this cell replacing the old one
     * @param ship placed in this cell
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    /**
     * Returns currently set ship or null if no ship is set
     * @return ship placed in this cell or null
     */
    public Ship getShip() {
        return ship;
    }
}
