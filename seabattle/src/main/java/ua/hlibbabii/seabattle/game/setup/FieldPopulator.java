package ua.hlibbabii.seabattle.game.setup;

import java.util.ArrayList;
import java.util.Collection;

import ua.hlibbabii.seabattle.game.domain.Cell;
import ua.hlibbabii.seabattle.game.domain.Field;
import ua.hlibbabii.seabattle.game.domain.Point;
import ua.hlibbabii.seabattle.game.domain.ship.Ship;
import ua.hlibbabii.seabattle.game.domain.ship.ShipOrientation;

/**
 * Created by hlib on 12.09.16.
 */
public class FieldPopulator {
    /**
     * Represents cells where the first index defines which row the cell is in
     * the second index - which column the cell is in
     */
    protected Cell[][] cells;

    protected Collection<Ship> ships = new ArrayList<>();

    public static class ShipCanNotBePlacedException extends RuntimeException {

        public ShipCanNotBePlacedException() {
        }

        public ShipCanNotBePlacedException(String s) {
            super(s);
        }
    }

    public FieldPopulator(int dimensions) {
        initCells(dimensions);
    }

    private void initCells(int dimensions) {
        this.cells = new Cell[dimensions][dimensions];
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                cells[i][j] = new Cell();
            }
        }
    }
    /* Checks whether cells where the ship should be placed as well as all adjacent cells are free*/
    protected boolean intersectsWithOtherShip(Ship ship) {
        Point startPoint = ship.getHead();
        int left = startPoint.getVer();
        int up = startPoint.getHor();
        int right = startPoint.getVer();
        int down = startPoint.getHor();
        int dimensions = getDimensions();
        int nDecks = ship.getNDecks();
        if (ship.getShipOrientation() == ShipOrientation.HORIZONTAL) {
            right = left + nDecks - 1;
        } else {
            down = up + nDecks -1;
        }
        if (right >= dimensions || down >= dimensions) {
            return false;
        }
        left -= 1;
        right += 1;
        up -= 1;
        down += 1;
        if (left < 0) {
            left = 0;
        }
        if (up < 0) {
            up = 0;
        }
        if (right >= dimensions) {
            right = dimensions - 1;
        }
        if (down >= dimensions) {
            down = dimensions - 1;
        }
        return areShipsInArea(left, up, right, down);
    }

    private boolean areShipsInArea(int left, int up, int right, int down) {
        for (int i = up; i <= down; i++) {
            for (int j = left; j <= right ; j++) {
                if (getShip(i, j) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void setShip(Ship ship) {
        if (intersectsWithOtherShip(ship)) {
            throw new ShipCanNotBePlacedException(String.format("%s, %s", ship, new Field(cells, ships)));
        }

        int nDecks = ship.getNDecks();
        Point point = ship.getHead();
        while (nDecks-- != 0) {
            cells[point.getHor()][point.getVer()].setShip(ship);
            point = (ship.getShipOrientation() == ShipOrientation.HORIZONTAL) ?
                    point.increaseVer() : point.increaseHor();

        }
        ships.add(ship);
        //TODO add check if the number of i-deck ships is not too big
    }

    protected Ship getShip(int h, int v) {
        return cells[h][v].getShip();
    }

    private int getDimensions() {
        return cells.length;
    }

    @Override
    public String toString() {
        return "Current state of field:\n" + (new Field(cells, ships)).toString();
    }
}
