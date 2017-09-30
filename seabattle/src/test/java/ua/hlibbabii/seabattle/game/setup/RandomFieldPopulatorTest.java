package ua.hlibbabii.seabattle.game.setup;

import junit.framework.Assert;

import org.junit.Test;

import ua.hlibbabii.seabattle.game.domain.Field;

import static org.junit.Assert.*;

/**
 * Created by hlib on 13.09.16.
 */
public class RandomFieldPopulatorTest {

    @Test
    public void testSetShipsByRandom() throws Exception {
        RandomFieldPopulator randomFieldPopulator = new RandomFieldPopulator(10);
        Field field = randomFieldPopulator.setShipsByRandom(new int[]{4, 3, 2, 1});
        System.out.println(field);
        Assert.assertEquals(field.getNumberOfShipsWithDecksNumber(1), 4);
        Assert.assertEquals(field.getNumberOfShipsWithDecksNumber(2), 3);
        Assert.assertEquals(field.getNumberOfShipsWithDecksNumber(3), 2);
        Assert.assertEquals(field.getNumberOfShipsWithDecksNumber(4), 1);
    }
}