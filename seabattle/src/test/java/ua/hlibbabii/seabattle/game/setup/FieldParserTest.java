package ua.hlibbabii.seabattle.game.setup;

import org.junit.Test;

import ua.hlibbabii.seabattle.game.domain.Field;
import ua.hlibbabii.seabattle.game.domain.FieldSnapshot;

/**
 * Created by hlib on 12.09.16.
 */
public class FieldParserTest {

    @Test
    public void testParseShips() throws Exception {
        FieldParser fieldParser = new FieldParser(10);
        Field field = fieldParser.parseShips(FieldSnapshot.of(
                new int[][]{
                        {1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {1, 1, 0, 1, 1, 0, 1, 0, 1, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {1, 1, 0, 1, 1, 0, 1, 0, 1, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},

                }
        ));
        System.out.println(field);
    }
}