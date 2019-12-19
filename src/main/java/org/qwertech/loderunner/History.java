package org.qwertech.loderunner;

import com.google.common.collect.EvictingQueue;
import lombok.extern.slf4j.Slf4j;
import org.qwertech.loderunner.api.BoardPoint;
import org.qwertech.loderunner.api.GameBoard;
import org.qwertech.loderunner.api.LoderunnerAction;

import java.util.ArrayList;
import java.util.List;

import static org.qwertech.loderunner.api.LoderunnerAction.SUICIDE;

/**
 * org.qwertech.loderunner.ActionsHistory.
 *
 * @author Pavel_Novikov
 */

@Slf4j
public class History {
    private static final EvictingQueue<BoardPoint> lastSteps = EvictingQueue.create(100);
    private static final EvictingQueue<LoderunnerAction> lastActions = EvictingQueue.create(100);
    private static final List<Runnable> killedListeners = new ArrayList<>();
    private static GameBoard PREV_GAME_BOARD = null;

    public static void addKilledListener(Runnable callback) {
        killedListeners.add(callback);
    }

    public static void writeMove(GameBoard gb, LoderunnerAction move) {
        History.lastSteps.add(gb.getMyPosition());
        PREV_GAME_BOARD = gb;
        History.lastActions.add(move);
    }

    public static BoardPoint getPrevPosition() {
        return getLastElement(lastSteps);
    }

    public static GameBoard getPrevBoard() {
        return PREV_GAME_BOARD;
    }

    public static LoderunnerAction stuckCorrect(LoderunnerAction action) {
        if (lastSteps.remainingCapacity() == 0 &&
                Main.allSame(lastSteps)) { // TODO check moved only in small region
            log.error("STUCK->SUICIDE");
            return SUICIDE;
        }
        return action;
    }

    public static <T> T getLastElement(final Iterable<T> elements) {
        T lastElement = null;

        for (T element : elements) {
            lastElement = element;
        }

        return lastElement;
    }
}
