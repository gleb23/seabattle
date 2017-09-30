package ua.hlibbabii.seabattle.game.domain;

import java.util.Arrays;
import java.util.EnumSet;

/**
 * Created by hlib on 13.10.16.
 */
public class FieldSnapshot {

    public Point getRandomClosedPoint() {
        Point point = Point.getRandom(0, getRows(), 0, getColumns());
        while (cells[point.getHor()][point.getVer()].isOpen()) {
            point = getNextPoint(point);
        }
        return point;
    }

    private Point getNextPoint(Point point) {
        if (point.getVer() < getColumns() - 1) {
            return point.increaseVer();
        } else if (point.getHor() < getRows() - 1) {
            return Point.create(point.getHor() + 1, 0);
        } else {
            return Point.create(0, 0);
        }
    }

    public enum CellProperty {
        UNKNOWN, OPEN, SHIP;
    };

    public static class CellSnapshot {
        private EnumSet<CellProperty> properties;

        private CellSnapshot(EnumSet<CellProperty> enumSet) {
            if (enumSet.contains(CellProperty.UNKNOWN) && enumSet.size() != 1) {
                throw new IllegalArgumentException("UNKNOWN should be the only property");
            }
            this.properties = EnumSet.copyOf(enumSet);
        }

        public static CellSnapshot of(int num) {
            EnumSet enumSet = EnumSet.noneOf(CellProperty.class);
            if ((num & 0b100) != 0) {
                enumSet.add(CellProperty.UNKNOWN);
            }
            if ((num & 0b010) != 0) {
                enumSet.add(CellProperty.OPEN);
            }
            if ((num & 0b001) != 0) {
                enumSet.add(CellProperty.SHIP);
            }
            return new CellSnapshot(enumSet);
        }

        public int toInt() {
            int num = 0;
            if (properties.contains(CellProperty.UNKNOWN)) {
                num += 0b100;
            }
            if (properties.contains(CellProperty.OPEN)) {
                num += 0b010;
            }
            if (properties.contains(CellProperty.SHIP)) {
                num += 0b001;
            }
            return num;
        }

        public boolean isShipPresent() {
            return properties.contains(CellProperty.SHIP);
        }

        public boolean isNothing() {
            return properties.contains(CellProperty.OPEN);
        }

        public boolean isOpen() {
            return properties.contains(CellProperty.OPEN);
        }

        public void openShip() {
            properties.add(CellProperty.OPEN);
            properties.add(CellProperty.SHIP);
        }

        public void openNothing() {
            properties.add(CellProperty.OPEN);
        }

        public void markShip() {
            if (!properties.contains(CellProperty.UNKNOWN)) {
                properties.add(CellProperty.SHIP);
            }
        }

        @Override
        public String toString() {
            return Integer.toString(toInt());
        }
    }

    private CellSnapshot[][] cells;

    public FieldSnapshot(CellSnapshot[][] cells) {
        this.cells = cells;
    }

    public static FieldSnapshot of(int[][] nums) {
        CellSnapshot[][] cells = new CellSnapshot[nums.length][nums[0].length];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[0].length; j++) {
                cells[i][j] = CellSnapshot.of(nums[i][j]);
            }
        }
        return new FieldSnapshot(cells);
    }

    public static FieldSnapshot createFieldSnapshotWithoutShips(int hor, int ver, boolean myField) {
        EnumSet<CellProperty> defaultProperties;
        if (myField) {
            defaultProperties = EnumSet.noneOf(CellProperty.class);
        } else {
            defaultProperties = EnumSet.of(CellProperty.UNKNOWN);
        }
        CellSnapshot[][] cells = new CellSnapshot[hor][ver];
        for (int i = 0; i < hor; i++) {
            for (int j = 0; j < ver; j++) {
                cells[i][j] = new CellSnapshot(defaultProperties);
            }
        }
        return new FieldSnapshot(cells);
    }

    public void openShip(int hor, int ver) {
        cells[hor][ver].openShip();
    }

    public void openNothing(int hor, int ver) {
        cells[hor][ver].openNothing();
    }

    public void markShip(int hor, int ver) {
        cells[hor][ver].markShip();
    }

    public boolean isShipPresent(Point point) {
        return cells[point.getHor()][point.getVer()].isShipPresent();
    }

    public boolean isNothing(int hor, int ver) {
        return cells[hor][ver].isNothing();
    }

    public boolean isOpen(Point point) {
        return cells[point.getHor()][point.getVer()].isOpen();
    }

    public int getRows() {
        return cells.length;
    }

    public int getColumns() {
        return cells[0].length;
    }

    public boolean isInside(Point point) {
        if (point.getHor() >= 0 && point.getHor() < getRows()) {
            if (point.getVer() >= 0 && point.getVer() < getColumns()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(cells);
    }
}
