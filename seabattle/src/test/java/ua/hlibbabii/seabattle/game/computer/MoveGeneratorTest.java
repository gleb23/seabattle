package ua.hlibbabii.seabattle.game.computer;

import org.junit.Test;

import ua.hlibbabii.seabattle.game.domain.FieldSnapshot;
import ua.hlibbabii.seabattle.game.domain.MoveResult;
import ua.hlibbabii.seabattle.game.domain.Point;

import static org.junit.Assert.*;

/**
 * Created by hlib on 14.10.16.
 */
public class MoveGeneratorTest {

    @Test
    public void testGetPointToShoot() throws Exception {
        FieldSnapshot fieldSnapshot = FieldSnapshot.of(new int[][]{
            {4,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2},
            {2,2,2,2,2,2,2,2,2,2}
        });

        Point actual = new MoveGenerator().getPointToShoot(fieldSnapshot, null);
        assertEquals(Point.create(0, 0), actual);
    }

    @Test
    public void testGetPointToShoot1() throws Exception {
        FieldSnapshot fieldSnapshot = FieldSnapshot.of(new int[][]{
                {2,2,2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2,2,2},
                {4,2,2,2,2,2,2,2,2,2}
        });

        Point actual = new MoveGenerator().getPointToShoot(fieldSnapshot, null);
        assertEquals(Point.create(9, 0), actual);
    }

    @Test
    public void testGetPointToShoot2() throws Exception {
        FieldSnapshot fieldSnapshot = FieldSnapshot.of(new int[][]{
                {4,4,4,4,4,3,4,4,4,4},
                {4,4,4,4,4,3,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4}
        });

        Point actual = new MoveGenerator().getPointToShoot(fieldSnapshot,
                new MoveResult(MoveResult.MoveResultStatus.WOUNDED,
                        Point.create(0, 5), 0, 1));
        assertEquals(Point.create(2, 5), actual);
    }

    public void testGetPointToShoot3() throws Exception {
        FieldSnapshot fieldSnapshot = FieldSnapshot.of(new int[][]{
                {4,4,4,4,4,3,4,4,4,4},
                {4,4,4,4,4,3,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4}
        });

        Point actual = new MoveGenerator().getPointToShoot(fieldSnapshot,
                new MoveResult(MoveResult.MoveResultStatus.WOUNDED,
                        Point.create(1, 5), 0, 1));
        assertEquals(Point.create(2, 5), actual);
    }
}