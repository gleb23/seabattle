package ua.hlibbabii.seabattle.game.domain;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import ua.hlibbabii.seabattle.GameObserver;
import ua.hlibbabii.seabattle.game.setup.FirstTurnStrategy;
import ua.hlibbabii.seabattle.game.setup.RandomFieldPopulator;

/**
 * Created by hlib on 04.09.16.
 */
public class Game {

    private Collection<GameObserver> gameObservers = new ArrayList<>();

    private int startingTurn;
    private int turn;

    private Field zeroPlayerField;
    private Field firstPlayerField;

    private BlockingQueue<Move> moveQueue = new ArrayBlockingQueue<Move>(100);

    public Game(GameOptions gameOptions) {
        startingTurn = findOutFirstTurn(gameOptions);
        turn = startingTurn;
        int[] maxNumberOfShips = gameOptions.getMaxNumberOfShips();
        int fieldDimension = gameOptions.getFieldDimension();
        zeroPlayerField = new RandomFieldPopulator(fieldDimension)
                .setShipsByRandom(maxNumberOfShips);
        firstPlayerField = new RandomFieldPopulator(fieldDimension)
                .setShipsByRandom(maxNumberOfShips);
    }

    private int findOutFirstTurn(GameOptions gameOptions) {
        FirstTurnStrategy firstTurnStrategy = gameOptions.getFirstTurnStrategy();
        if (firstTurnStrategy == FirstTurnStrategy.RANDOM) {
            return (int) System.currentTimeMillis() % 2;
        } else if (firstTurnStrategy == FirstTurnStrategy.PLAYER_FIRST) {
            return 0;
        } else {
            return 1;
        }
    }

    public void start() {
        new Thread(() -> {
            try {
                while(true) {
                    Move move = moveQueue.take();
                    MoveResult moveResult = processMove(move);
                    notifyObservers(moveResult);
                }
            } catch (InterruptedException e) {}
        }).start();
    }

    /**
     * returns the first player's fieldSnapshot
     *
     * @return the first player's fieldSnapshot
     */
    public Field getZeroPlayerField() {
        return this.zeroPlayerField;
    }

    /**
     * returns the second player's fieldSnapshot
     *
     * @return the second player's fieldSnapshot
     */
    public Field getFirstPlayerField() {
        return this.firstPlayerField;
    }

    public int getTurn() {
        return turn;
    }

    private MoveResult.MoveResultStatus shoot(Point point) {
        Field filedToShoot = (turn == 0 ? firstPlayerField : zeroPlayerField);
        return filedToShoot.shoot(point);
    }

    private void changeTurn() {
        turn ^= 1;
    }

    public void makeMove(Point point, int playerNumber) {
        makeMove(Move.create(point, playerNumber));
    }

    public void makeMove(Move move) {
        try {
            moveQueue.put(move);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private MoveResult processMove(Move move) {
        Log.d("Processing Move: ", move.toString());
        Point point = move.getPoint();
        int turnOfCurrentMove = move.getPlayer();
        if (turnOfCurrentMove != turn) {
            return new MoveResult(MoveResult.MoveResultStatus.NOT_YOUR_TURN, point,
                    turnOfCurrentMove, turn);
        }

        try {
            MoveResult.MoveResultStatus moveResultStatus = shoot(point);
            if (moveResultStatus == MoveResult.MoveResultStatus.MISSED) {
                changeTurn();
            }
            return new MoveResult(moveResultStatus, point, turnOfCurrentMove, turn);

        } catch (Field.CellAlreadyOpenedException e) {
            return new MoveResult(MoveResult.MoveResultStatus.CELL_ALREADY_OPENED, point,
                    turnOfCurrentMove, turn);
        }
    }

    public void addObserver(GameObserver gameObserver) {
        gameObservers.add(gameObserver);
    }

    /* called from worker thread*/
    public void notifyObservers(MoveResult lastMoveResult) {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.updateLastMoveResult(lastMoveResult);
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "turn=" + turn +
                ", zeroPlayerField=" + zeroPlayerField +
                ", firstPlayerField=" + firstPlayerField +
                '}';
    }

    public FieldSnapshot getPlayerFieldSnapshotForComputer() {
        return getZeroPlayerField().toOpponentFieldSnapshot();
    }

    public FieldSnapshot getPlayerFieldSnapshotForPlayer() {
        return getZeroPlayerField().toMyFieldSnapshot();
    }

    public boolean isMyTurn(int playerNumberAssigned) {
        return turn == playerNumberAssigned;
    }

    public FieldSnapshot getComputerFieldSnapshotForPlayer() {
        return getFirstPlayerField().toOpponentFieldSnapshot();
    }
}
