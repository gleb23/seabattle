package ua.hlibbabii.seabattle.game.computer;

import org.junit.Test;

import ua.hlibbabii.seabattle.game.domain.GameOptions;
import ua.hlibbabii.seabattle.game.domain.MoveResult;
import ua.hlibbabii.seabattle.game.domain.Point;
import ua.hlibbabii.seabattle.game.setup.FirstTurnStrategy;

/**
 * Created by hlib on 19.10.16.
 */
public class ComputerTest {

    private MoveGenerator moveGenerator;

    @Test
    public void testUpdateLastMoveResult() throws Exception {
        /* given */
        GameOptions gameOptions = new GameOptions();
        gameOptions.setFirstTurnStrategy(FirstTurnStrategy.PLAYER_FIRST);

        Computer computer = new Computer(1, game);
        MoveResult lastMoveResult = new MoveResult(MoveResult.MoveResultStatus.MISSED,
                Point.create(0, 0), 0, 1);

        /* when */
        computer.updateLastMoveResult(lastMoveResult);

        /* then */


    }
}