package ua.hlibbabii.seabattle.game.domain;

import java.io.Serializable;

/**
 * Created by hlib on 04.09.16.
 */
public class Move {
    private final Point point;
    private final int player;

    private Move(Point point, int player) {
        this.point = point;
        this.player = player;
    }

    public static Move create(Point point, int player) {
        return new Move(point, player);
    }

    public Point getPoint() {
        return point;
    }

    public int getPlayer() {
        return player;
    }
}
