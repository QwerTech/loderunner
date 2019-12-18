package org.qwertech.loderunner;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.qwertech.loderunner.api.BoardElement;
import org.qwertech.loderunner.api.LoderunnerAction;

/**
 * MoveActionCheckerTest.
 *
 * @author Pavel_Novikov
 */
public class MoveActionCheckerTest {

    @Test
    public void moveUpOnLeader() {
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_UP, BoardElement.LADDER, BoardElement.HERO_LADDER, BoardElement.BRICK));
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_UP, BoardElement.NONE, BoardElement.HERO_LADDER, BoardElement.LADDER));
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_UP, BoardElement.NONE, BoardElement.HERO_LADDER, BoardElement.BRICK));
    }
    @Test
    public void moveDownOnLeader() {
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_DOWN, BoardElement.LADDER, BoardElement.HERO_LADDER, BoardElement.LADDER));
    }
    @Test
    public void moveDownToLeader() {
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_DOWN, BoardElement.LADDER, BoardElement.HERO_LEFT, BoardElement.LADDER));
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_DOWN, BoardElement.LADDER, BoardElement.HERO_RIGHT, BoardElement.LADDER));
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_DOWN, BoardElement.LADDER, BoardElement.NONE, BoardElement.LADDER));
    }
    @Test
    public void moveUpToGold() {
        assertFalse(MoveActionChecker.canMove(LoderunnerAction.GO_UP, BoardElement.GREEN_GOLD, BoardElement.HERO_RIGHT, BoardElement.BRICK));
        assertFalse(MoveActionChecker.canMove(LoderunnerAction.GO_UP, BoardElement.GREEN_GOLD, BoardElement.NONE, BoardElement.BRICK));
    }
    @Test
    public void moveLeftOnEmptyFromLadder() {
        assertFalse(MoveActionChecker.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.NONE, BoardElement.PIPE));
        assertFalse(MoveActionChecker.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.HERO_LEFT, BoardElement.PIPE));
        assertFalse(MoveActionChecker.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.HERO_LEFT, BoardElement.NONE));
        assertFalse(MoveActionChecker.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.NONE, BoardElement.NONE));
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.NONE, BoardElement.HERO_LADDER));
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.NONE, BoardElement.LADDER));
    }
    @Test
    public void moveOnAir() {
        assertFalse(MoveActionChecker.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.NONE, BoardElement.NONE));
        assertFalse(MoveActionChecker.canMove(LoderunnerAction.GO_LEFT, BoardElement.GREEN_GOLD, BoardElement.NONE, BoardElement.NONE));
        assertFalse(MoveActionChecker.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.NONE, BoardElement.PIPE));
        assertFalse(MoveActionChecker.canMove(LoderunnerAction.GO_UP, BoardElement.NONE, BoardElement.HERO_LEFT, BoardElement.BRICK));
        assertFalse(MoveActionChecker.canMove(LoderunnerAction.GO_UP, BoardElement.GREEN_GOLD, BoardElement.HERO_LEFT, BoardElement.BRICK));
        assertFalse(MoveActionChecker.canMove(LoderunnerAction.GO_RIGHT, BoardElement.GREEN_GOLD, BoardElement.NONE, BoardElement.HERO_LEFT));
    }
    @Test
    public void moveOnPipe() {
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_LEFT, BoardElement.PIPE, BoardElement.PIPE, BoardElement.NONE));
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_LEFT, BoardElement.PIPE, BoardElement.PIPE, BoardElement.NONE));

        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_DOWN, BoardElement.NONE, BoardElement.PIPE, BoardElement.NONE));

        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_DOWN, BoardElement.NONE, BoardElement.HERO_PIPE_LEFT, BoardElement.NONE));
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_DOWN, BoardElement.NONE, BoardElement.HERO_PIPE_RIGHT, BoardElement.NONE));

        assertFalse(MoveActionChecker.canMove(LoderunnerAction.GO_RIGHT, BoardElement.NONE, BoardElement.NONE, BoardElement.PIPE));
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_RIGHT, BoardElement.NONE, BoardElement.PIPE, BoardElement.NONE));
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_RIGHT, BoardElement.GREEN_GOLD, BoardElement.PIPE, BoardElement.NONE));
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_RIGHT, BoardElement.NONE, BoardElement.HERO_PIPE_RIGHT, BoardElement.NONE));
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_RIGHT, BoardElement.GREEN_GOLD, BoardElement.HERO_PIPE_RIGHT, BoardElement.NONE));
        assertFalse(MoveActionChecker.canMove(LoderunnerAction.GO_RIGHT, BoardElement.PIPE, BoardElement.HERO_RIGHT, BoardElement.NONE));
        assertTrue(MoveActionChecker.canMove(LoderunnerAction.GO_RIGHT, BoardElement.PIPE, BoardElement.HERO_PIPE_RIGHT, BoardElement.NONE));
    }

}