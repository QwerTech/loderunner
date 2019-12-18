package org.qwertech.loderunner;

import static java.util.Arrays.asList;
import static org.qwertech.loderunner.api.BoardElement.BRICK;
import static org.qwertech.loderunner.api.BoardElement.HERO_DIE;
import static org.qwertech.loderunner.api.BoardElement.HERO_DRILL_LEFT;
import static org.qwertech.loderunner.api.BoardElement.HERO_DRILL_RIGHT;
import static org.qwertech.loderunner.api.BoardElement.HERO_FALL_LEFT;
import static org.qwertech.loderunner.api.BoardElement.HERO_FALL_RIGHT;
import static org.qwertech.loderunner.api.BoardElement.HERO_LEFT;
import static org.qwertech.loderunner.api.BoardElement.HERO_PIPE_LEFT;
import static org.qwertech.loderunner.api.BoardElement.HERO_PIPE_RIGHT;
import static org.qwertech.loderunner.api.BoardElement.HERO_RIGHT;
import static org.qwertech.loderunner.api.BoardElement.LADDER;
import static org.qwertech.loderunner.api.BoardElement.NONE;
import static org.qwertech.loderunner.api.BoardElement.PIPE;
import static org.qwertech.loderunner.api.BoardElement.UNDESTROYABLE_WALL;
import static org.qwertech.loderunner.api.LoderunnerAction.GO_DOWN;

import lombok.RequiredArgsConstructor;
import org.qwertech.loderunner.api.BoardElement;
import org.qwertech.loderunner.api.BoardPoint;
import org.qwertech.loderunner.api.GameBoard;
import org.qwertech.loderunner.api.LoderunnerAction;

import java.util.List;

/**
 * DigActionChecker.
 *
 * @author Pavel_Novikov
 */
@RequiredArgsConstructor
public class MoveActionChecker {

    private final DrillActionChecker drillActionChecker = new DrillActionChecker();
    private final GameBoard gb;

    public boolean canMoveTo(BoardPoint from, LoderunnerAction action) {

        BoardPoint to = action.getTo(from);
        BoardPoint under = from.shiftBottom();
        BoardPoint overTo = to.shiftTop();

        if (gb.isOutOfBoard(overTo, under, to)) {
            return false;
        }

        BoardElement fromElement = gb.getElementAt(from);
        BoardElement underElement = gb.getElementAt(under);
        BoardElement toElement = gb.getElementAt(to);
        BoardElement overToElement = gb.getElementAt(overTo);

        return canMove(action, toElement, fromElement, underElement) || drillActionChecker.canMove(action, toElement, fromElement, overToElement);
    }

    protected static boolean canMove(LoderunnerAction action, BoardElement toElement, BoardElement fromElement,
                                     BoardElement underFromElement) {
        List<BoardElement> fallIfUnderIsHero = asList(HERO_DRILL_LEFT,
                HERO_DRILL_RIGHT,
                HERO_LEFT,
                HERO_RIGHT,
                HERO_FALL_LEFT,
                HERO_FALL_RIGHT,
                HERO_PIPE_LEFT,
                HERO_PIPE_RIGHT);
        List<BoardElement> heroOnNothing = asList(HERO_DIE,
                HERO_DRILL_LEFT,
                HERO_DRILL_RIGHT,
                HERO_LEFT,
                HERO_RIGHT,
                HERO_FALL_LEFT,
                HERO_FALL_RIGHT);
        if ((underFromElement.isNoneOrGold() || underFromElement.isPipe() || fallIfUnderIsHero.contains(underFromElement))
                && (fromElement.isNoneOrGold() || heroOnNothing.contains(fromElement) || fromElement.isPit() || fromElement.isBrick())) {
            return action.down();
        }

        if (toElement.equals(UNDESTROYABLE_WALL)) {
            return false;
        }

        if (toElement.isNoneOrGold()) {
            if (action.horizontal()) {
                return true;
            }
            if ((fromElement.isLadder()) && action.equals(GO_DOWN)) {
                return true;
            }
            if (fromElement.isLadder() && (action.up() || action.horizontal())) {
                return true;
            }
            if (fromElement.equals(NONE) && action.down()) {
                return true;
            }
        }
        if (toElement.equals(LADDER)) {
            if ((fromElement.isLadder()) && action.vertical()) {
                return true;
            }
            if ((fromElement.isNoneOrGold() || fromElement.isHero()) && (action.horizontal() || action.down())) {
                return true;
            }
        }
        if (toElement.equals(PIPE)) {
            if (action.horizontal() || action.down()) {
                return true;
            }
        }
        if (fromElement.equals(PIPE) || fromElement.equals(HERO_PIPE_LEFT) || fromElement.equals(HERO_PIPE_RIGHT)) {
            if (action.horizontal() && toElement.equals(PIPE)) {
                return true;
            }
            if (toElement.isNoneOrGold() && action.down()) {
                return true;
            }
        }

        if (toElement.isPit() && action.equals(GO_DOWN)) {
            return true;
        }
        if ((fromElement.isBrick() || fromElement.isPit()) && action.equals(GO_DOWN)
                && !asList(BRICK, UNDESTROYABLE_WALL).contains(toElement) && !toElement.isOtherHero() && !toElement.isEnemy()) {
            return true;
        }
        return false;
    }
}
