package ua.hlibbabii.seabattle.game.computer;

import java.io.Serializable;

import ua.hlibbabii.seabattle.GameObserver;
import ua.hlibbabii.seabattle.game.domain.Game;
import ua.hlibbabii.seabattle.game.domain.Move;
import ua.hlibbabii.seabattle.game.domain.MoveResult;
import ua.hlibbabii.seabattle.game.domain.Point;

/**
 * Created by hlib on 12.10.16.
 */
public class Computer implements GameObserver {
    private Game game;
    private int playerNumberAssigned;

    private MoveGenerator moveGenerator = new MoveGenerator();

    public Computer(int playerNumberAssigned, Game game) {
        this.game = game;
        this.playerNumberAssigned = playerNumberAssigned;
    }

    /* called from worker thread */
    @Override
    public void updateLastMoveResult(MoveResult lastMoveResult) {
        if (game.isMyTurn(playerNumberAssigned) && !lastMoveResult.isFinalMove()) {
            Point nextPoint = moveGenerator.getPointToShoot(game.getPlayerFieldSnapshotForComputer(), lastMoveResult);
            pretendThinking(3000);
            game.makeMove(nextPoint, playerNumberAssigned);
        }
    }

    private void pretendThinking(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {

        }
    }
}
