package org.qwertech.loderunner;

import static org.qwertech.loderunner.api.LoderunnerAction.SUICIDE;

import com.google.common.collect.EvictingQueue;
import lombok.extern.slf4j.Slf4j;
import org.qwertech.loderunner.Main;
import org.qwertech.loderunner.api.BoardPoint;
import org.qwertech.loderunner.api.GameBoard;
import org.qwertech.loderunner.api.LoderunnerAction;

/**
 * org.qwertech.loderunner.ActionsHistory.
 *
 * @author Pavel_Novikov
 */

@Slf4j
public class ActionsHistory {
    private static final EvictingQueue<BoardPoint> lastSteps = EvictingQueue.create(100);
    private static final EvictingQueue<LoderunnerAction> lastActions = EvictingQueue.create(100);
//TODO store previous board
    public static void writeMove(GameBoard gb, LoderunnerAction move) {
        ActionsHistory.lastSteps.add(gb.getMyPosition());
        ActionsHistory.lastActions.add(move);
    }
    public static LoderunnerAction stuckCorrect(LoderunnerAction action) {
        if (lastSteps.remainingCapacity() == 0 &&
                Main.allSame(lastSteps)) { // TODO check moved only in small region
            log.error("STUCK->SUICIDE");
            return SUICIDE;
        }
        return action;
    }
}
