package org.qwertech.loderunner;

import static org.junit.Assert.*;

import org.junit.Test;
import org.qwertech.loderunner.api.BoardElement;
import org.qwertech.loderunner.api.GameBoard;
import org.qwertech.loderunner.api.LoderunnerAction;

/**
 * DrillActionCheckerTest.
 *
 * @author Pavel_Novikov
 */
public class DrillActionCheckerTest {

    @Test
    public void cantDrillUnderLadder() {
        assertFalse(new DrillActionChecker().canMove(LoderunnerAction.DRILL_RIGHT, BoardElement.BRICK, BoardElement.NONE, BoardElement.LADDER));
    }
    @Test
    public void cantDrillUnderBrickOrWall() {
        assertFalse(new DrillActionChecker().canMove(LoderunnerAction.DRILL_RIGHT, BoardElement.BRICK, BoardElement.NONE, BoardElement.BRICK));
        assertFalse(new DrillActionChecker().canMove(LoderunnerAction.DRILL_RIGHT, BoardElement.BRICK, BoardElement.NONE, BoardElement.UNDESTROYABLE_WALL));
    }
    @Test
    public void cantDrillUnderOtherPlayer() {
        assertFalse(new DrillActionChecker().canMove(LoderunnerAction.DRILL_RIGHT, BoardElement.BRICK, BoardElement.NONE, BoardElement.OTHER_HERO_LEFT));
    }
}