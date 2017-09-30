package ua.hlibbabii.seabattle.game.domain;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hlib on 26.10.16.
 */
public class FieldSnapshotTest {

    @Test
    public void testToString() throws Exception {
        System.out.println(FieldSnapshot.of(new int[][]{{1, 2}, {3, 4}}).toString());
    }

    @Test
    public void testCreateFieldSnapshotWithoutShips() {
        FieldSnapshot fieldSnapshotWithoutShips = FieldSnapshot.createFieldSnapshotWithoutShips(2, 2, true);
        System.out.println(fieldSnapshotWithoutShips.toString());
    }

    @Test
    public void testCreateFieldSnapshotWithoutShips2() {
        FieldSnapshot fieldSnapshotWithoutShips = FieldSnapshot.createFieldSnapshotWithoutShips(2, 2, false);
        System.out.println(fieldSnapshotWithoutShips.toString());
    }
}