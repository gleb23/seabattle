package ua.hlibbabii.seabattle.game.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import ua.hlibbabii.seabattle.game.domain.ship.Ship;

/**
 * Created by hlib on 04.09.16.
 */
public class Field {

    /**
     * This class represents an exception that is thrown when the cell
     *  to be opened is already opened.
     *
     * @author Gleb
     *
     */
    public static class CellAlreadyOpenedException extends RuntimeException {

        public CellAlreadyOpenedException() {
        }
    }

    /**
     * Represents cells where the first index defines which row the cell is in
     * the second index - which column the cell is in
     */
    private Cell[][] cells;

    private Collection<Ship> ships = new ArrayList<>();

    /* Creates empty fieldSnapshot*/
    public Field(Cell[][] cells, Collection<Ship> ships) {
        this.cells = cells;
        this.ships = ships;
    }

    public boolean isOpen(Point point) {
        return cells[point.getHor()][point.getVer()].isOpen();
    }

    private void open(Point point) {
        cells[point.getHor()][point.getVer()].open(true);
    }


    public MoveResult.MoveResultStatus shoot(Point point) throws CellAlreadyOpenedException {
        /*
         * Открываем саму клетку, в которую стреляем
         */
        if (!isOpen(point)) {
            open(point);
        } else {
            throw new CellAlreadyOpenedException();
        }

        /*
         * Убиваем одну палубу корабля, если попали в корабль
         */
        Ship ship = getShip(point);
        if (ship != null) {
            ship.killOneDeck();
            if (ship.isKilled()) {
                return shipsNotKilled() != 0 ? MoveResult.MoveResultStatus.KILLED :
                    MoveResult.MoveResultStatus.WIN;
            } else {
                return MoveResult.MoveResultStatus.WOUNDED;

            }
        } else {
            return MoveResult.MoveResultStatus.MISSED;
        }
    }

    public int shipsNotKilled() {
        int notKilledShips = 0;
        for (Ship ship : ships) {
            if (!ship.isKilled()) {
                ++notKilledShips;
            }
        }
        return notKilledShips;
    }

    public int getNumberOfShipsWithDecksNumber(int n) {
        int nShips = 0;
        for (Ship ship : ships) {
            if (ship.getNDecks() == n) {
                ++nShips;
            }
        }
        return nShips;
    }

    public Ship getShip(Point point) {
        return cells[point.getHor()][point.getVer()].getShip();
    }

    public int getDimensions() {
        return cells.length;
    }



    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\n");
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j].getShip() != null) {
                    str.append("1");
                } else {
                    str.append("0");
                }
            }
            str.append("\n");
        }
        return str.toString();
    }

    public FieldSnapshot toFieldSnapshot(boolean myField) {
        FieldSnapshot fieldSnapshot = FieldSnapshot.createFieldSnapshotWithoutShips(cells.length, cells[0].length, myField);
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                boolean shipPresent = getShip(Point.create(i, j)) != null;
                boolean cellIsOpen = cells[i][j].isOpen();
                if (shipPresent && cellIsOpen) {
                    fieldSnapshot.openShip(i, j);
                } else if (cellIsOpen) {
                    fieldSnapshot.openNothing(i, j);
                } else if (shipPresent) { // ship
                    fieldSnapshot.markShip(i, j);
                }
            }
        }
        return fieldSnapshot;
    }

    public FieldSnapshot toOpponentFieldSnapshot() {
        return toFieldSnapshot(false);
    }

    public FieldSnapshot toMyFieldSnapshot() {
        return toFieldSnapshot(true);
    }
}
