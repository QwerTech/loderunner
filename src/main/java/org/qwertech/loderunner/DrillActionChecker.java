package org.qwertech.loderunner;

import static java.util.Arrays.asList;
import static org.qwertech.loderunner.api.BoardElement.BRICK;
import static org.qwertech.loderunner.api.BoardElement.LADDER;
import static org.qwertech.loderunner.api.BoardElement.NONE;
import static org.qwertech.loderunner.api.BoardElement.UNDESTROYABLE_WALL;
import static org.qwertech.loderunner.api.BoardElement.otherHeroes;
import static org.qwertech.loderunner.api.LoderunnerAction.DRILL_LEFT;
import static org.qwertech.loderunner.api.LoderunnerAction.DRILL_RIGHT;

import org.qwertech.loderunner.api.BoardElement;
import org.qwertech.loderunner.api.LoderunnerAction;

/**
 * DigActionChecker.
 *
 * @author Pavel_Novikov
 */
public class DrillActionChecker {
    public boolean canMove(LoderunnerAction action, BoardElement toElement, BoardElement fromElement, BoardElement overToElement) {
        if (!toElement.equals(BRICK) || !asList(DRILL_LEFT, DRILL_RIGHT).contains(action) || overToElement.isLadder() ||
                asList(BRICK, UNDESTROYABLE_WALL).contains(overToElement) || otherHeroes.contains(overToElement)) {
            return false;
        }
        if (asList(NONE, LADDER).contains(fromElement) || fromElement.isHero() || fromElement.isGold()) {
            return true;
        }
        return false;
    }
}
