package org.qwertech.loderunner;

import lombok.extern.slf4j.Slf4j;
import org.qwertech.loderunner.api.BoardPoint;
import org.qwertech.loderunner.api.GameBoard;

import java.util.Objects;

/**
 * org.qwertech.loderunner.MovesFromKilled.
 *
 * @author Pavel_Novikov
 */
@Slf4j
public class MovesFromKilled {
    public static int getMovesFromKilled() {
        return movesFromKilled;
    }

    public static int movesFromKilled = 0;
    private static BoardPoint prevPosition = null;

    public static void analyseMovesFromKilled(GameBoard gb) {
        BoardPoint myPosition = gb.getMyPosition();
        if (prevPosition != null && (Objects.equals(myPosition, prevPosition) || prevPosition.distance(myPosition) > 4)) {
            log.error("Get killed( Hero was alive for {} moves", movesFromKilled);
            movesFromKilled = 0;
        }
        prevPosition = myPosition;
        movesFromKilled++;
    }
}
