package ua.hlibbabii.seabattle.game.domain;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by hlib on 11.09.16.
 */
public class Point {
    private static final Random rand = new Random();

    private final int hor;
    private final int ver;

    public Point(int hor, int ver) {
        this.ver = ver;
        this.hor = hor;
    }

    public static Point create(int hor, int ver) {
        return new Point(hor, ver);
    }

    public int getHor() {
        return hor;
    }

    public int getVer() {
        return ver;
    }

    public static Point getRandom(int horFrom, int horTo, int verFrom, int verTo) {
        int h = rand.nextInt(horTo) + horFrom;
        int v = rand.nextInt(verTo) + verFrom;
        return create(h, v);
    }

    public Point increaseVer() {
        return create(hor, ver + 1);
    }

    public Point increaseHor() {
        return create(hor + 1, ver);
    }

    public Point decreaseHor() {
        return create(hor - 1, ver);
    }

    public Point decreaseVer() {
        return create(hor, ver - 1);
    }

    @Override
    public String toString() {
        return "Point{" +
                "hor=" + hor +
                ", ver=" + ver +
                '}';
    }

    public Point increaseVer(int num) {
        return create(hor, ver + num);
    }

    public Point increaseHor(int num) {
        return create(hor + num, ver);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (hor != point.hor) return false;
        return ver == point.ver;

    }

    @Override
    public int hashCode() {
        int result = hor;
        result = 31 * result + ver;
        return result;
    }

    public int toInt(int base) {
        return hor * base + ver;
    }
}
