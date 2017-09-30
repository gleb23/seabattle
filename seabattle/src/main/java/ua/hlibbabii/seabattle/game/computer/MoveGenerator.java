package ua.hlibbabii.seabattle.game.computer;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ua.hlibbabii.seabattle.game.domain.FieldSnapshot;
import ua.hlibbabii.seabattle.game.domain.MoveResult;
import ua.hlibbabii.seabattle.game.domain.Point;
import ua.hlibbabii.seabattle.game.domain.ship.ShipOrientation;

/**
 * Created by hlib on 12.10.16.
 */
public class MoveGenerator {
    public Point getPointToShoot(FieldSnapshot fieldSnapshot, MoveResult lastMoveResult) {
        if (lastMoveResult != null &&
                lastMoveResult.getMoveResultStatus() == MoveResult.MoveResultStatus.WOUNDED) {
            Point point = lastMoveResult.getPoint();
            ShipPart shipPart = getShipPart(fieldSnapshot, point);
            List<Point> pointsToFinishShip = getPointsToFinishShip(fieldSnapshot, shipPart);

            /* choosing random point from the list */
            Collections.shuffle(pointsToFinishShip);
            return pointsToFinishShip.get(0);
        } else {
            return fieldSnapshot.getRandomClosedPoint();
        }
    }

    private List<Point> getPointsToFinishShip(FieldSnapshot fieldSnapshot, ShipPart shipPart) {
        List<Point> applicants = new ArrayList<>();
        Point head = shipPart.getCurrentHead();
        if (shipPart.getCurrentDecksNumber() == 1) {
            applicants = Arrays.asList(head.decreaseHor(), head.decreaseVer(),
                    head.increaseHor(), head.increaseVer());
        } else if (shipPart.getShipOrientation() == ShipOrientation.HORIZONTAL) {
             applicants = Arrays.asList(head.decreaseVer(),
                     head.increaseVer(shipPart.getCurrentDecksNumber()));
        } else if (shipPart.getShipOrientation() == ShipOrientation.VERTICAL) {
            applicants = Arrays.asList(head.decreaseHor(),
                    head.increaseHor(shipPart.getCurrentDecksNumber()));
        }

        /* Check if every point fit the fieldSnapshot */
        List<Point> res = new ArrayList<>();
        for (Point applicant : applicants) {
            if (fieldSnapshot.isInside(applicant)) {
                res.add(applicant);
            }
        }
        return res;
    }

    private ShipPart getShipPart(FieldSnapshot fieldSnapshot, Point point) {
        ShipPart shipPart = ShipPart.createFromPoint(point);

        /* Checking if there are killed parts of the ship above */
        Point movingPoint = point;
        while (movingPoint.getHor() > 0) {
            movingPoint = movingPoint.decreaseHor();
            if (fieldSnapshot.isShipPresent(movingPoint)) {
                shipPart = shipPart.incrementUp();
            } else {
                break;
            }
        }

        /* Checking if there are killed parts of the ship below */
        movingPoint = point;
        while (movingPoint.getHor() < fieldSnapshot.getRows() - 1) {
            movingPoint = movingPoint.increaseHor();
            if (fieldSnapshot.isShipPresent(movingPoint)) {
                shipPart = shipPart.incrementDown();
            } else {
                break;
            }
        }

        if (shipPart.getCurrentDecksNumber() == 1) {
            /* The orientation of the ship is not necessarily vertical */

            /* Checking if there is killed parts of the ship to the left */
            movingPoint = point;
            while (movingPoint.getVer() > 0) {
                movingPoint = movingPoint.decreaseVer();
                if (fieldSnapshot.isShipPresent(movingPoint)) {
                    shipPart = shipPart.incrementLeft();
                } else {
                    break;
                }
            }

            /* Checking if there is killed parts of the ship to the right */
            movingPoint = point;
            while (movingPoint.getVer() < fieldSnapshot.getColumns() - 1) {
                movingPoint = movingPoint.increaseVer();
                if (fieldSnapshot.isShipPresent(movingPoint)) {
                    shipPart = shipPart.incrementRight();
                } else {
                    break;
                }
            }
        }


        return shipPart;
    }
}
