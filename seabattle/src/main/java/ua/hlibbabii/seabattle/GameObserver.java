package ua.hlibbabii.seabattle;

import ua.hlibbabii.seabattle.game.domain.MoveResult;

/**
 * Created by hlib on 15.09.16.
 */
public interface GameObserver {
    void updateLastMoveResult(MoveResult lastMoveResult);
}
