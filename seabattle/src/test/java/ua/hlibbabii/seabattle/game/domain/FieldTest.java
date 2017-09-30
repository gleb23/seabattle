package ua.hlibbabii.seabattle.game.domain;

import org.junit.Test;

import ua.hlibbabii.seabattle.game.setup.RandomFieldPopulator;

import static org.junit.Assert.*;

/**
 * Created by hlib on 26.10.16.
 */
public class FieldTest {

    @Test
    public void testToFieldSnapshot() {
        System.out.println(new RandomFieldPopulator(3).setShipsByRandom(new int[]{0, 1, 1, 0})
                .toFieldSnapshot(true));
    }

}