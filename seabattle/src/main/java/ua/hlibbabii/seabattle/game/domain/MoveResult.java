package ua.hlibbabii.seabattle.game.domain;

/**
 * Created by hlib on 04.09.16.
 */
public class MoveResult {

    public boolean isFinalMove() {
        return moveResultStatus == MoveResultStatus.WIN;
    }

    public boolean shipHit() {
        if (!this.moveResultStatus.moveSucceded()) {
            throw new MoveNotSuceededException();
        }
        return this.moveResultStatus.shipHit();
    }

    public boolean isMoveSucceeded() {
        return this.moveResultStatus.moveSucceded();
    }

    public enum MoveResultStatus {
        KILLED,
        WOUNDED,
        MISSED,
        WIN,

        CELL_ALREADY_OPENED,
        NOT_YOUR_TURN;

        public boolean moveSucceded() {
            return (this == KILLED || this == WOUNDED ||this == MISSED || this == WIN);
        }

        public boolean shipHit() {
            return (this == KILLED || this == WOUNDED || this == WIN);
        }
    }

    private MoveResultStatus moveResultStatus;

    private Point point;

    private int turn;

    private int nextTurn;

    public MoveResult(MoveResultStatus moveResultStatus, Point point, int turn, int nextTurn) {
        this.moveResultStatus = moveResultStatus;
        this.point = point;
        this.turn = turn;
        this.nextTurn = nextTurn;
    }

    public MoveResultStatus getMoveResultStatus() {
        return moveResultStatus;
    }

    public void setMoveResultStatus(MoveResultStatus moveResultStatus) {
        this.moveResultStatus = moveResultStatus;
    }

    public int getNextTurn() {
        return nextTurn;
    }

    public int getTurn() {
        return turn;
    }

    public void setNextTurn(int nextTurn) {
        this.nextTurn = nextTurn;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public String toString() {
        return "MoveResult{" +
                "moveResultStatus=" + moveResultStatus +
                ", point=" + point +
                ", nextTurn=" + nextTurn +
                '}';
    }

    private class MoveNotSuceededException extends RuntimeException {
    }
}
