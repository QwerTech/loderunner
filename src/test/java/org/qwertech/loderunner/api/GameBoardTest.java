package org.qwertech.loderunner.api;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * GameBoardTest.
 *
 * @author Pavel_Novikov
 */
public class GameBoardTest {

    @Test
    public void moveUpOnLeader() {
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_UP, BoardElement.LADDER, BoardElement.HERO_LADDER, BoardElement.BRICK));
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_UP, BoardElement.NONE, BoardElement.HERO_LADDER, BoardElement.LADDER));
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_UP, BoardElement.NONE, BoardElement.HERO_LADDER, BoardElement.BRICK));
    }
    @Test
    public void moveDownOnLeader() {
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_DOWN, BoardElement.LADDER, BoardElement.HERO_LADDER, BoardElement.LADDER));
    }
    @Test
    public void moveDownToLeader() {
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_DOWN, BoardElement.LADDER, BoardElement.HERO_LEFT, BoardElement.LADDER));
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_DOWN, BoardElement.LADDER, BoardElement.HERO_RIGHT, BoardElement.LADDER));
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_DOWN, BoardElement.LADDER, BoardElement.NONE, BoardElement.LADDER));
    }
    @Test
    public void moveUpToGold() {
        assertFalse(GameBoard.canMove(LoderunnerAction.GO_UP, BoardElement.GREEN_GOLD, BoardElement.HERO_RIGHT, BoardElement.BRICK));
        assertFalse(GameBoard.canMove(LoderunnerAction.GO_UP, BoardElement.GREEN_GOLD, BoardElement.NONE, BoardElement.BRICK));
    }
    @Test
    public void moveLeftOnEmptyFromLadder() {
        assertFalse(GameBoard.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.NONE, BoardElement.PIPE));
        assertFalse(GameBoard.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.HERO_LEFT, BoardElement.PIPE));
        assertFalse(GameBoard.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.HERO_LEFT, BoardElement.NONE));
        assertFalse(GameBoard.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.NONE, BoardElement.NONE));
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.NONE, BoardElement.HERO_LADDER));
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.NONE, BoardElement.LADDER));
    }
    @Test
    public void moveOnAir() {
        assertFalse(GameBoard.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.NONE, BoardElement.NONE));
        assertFalse(GameBoard.canMove(LoderunnerAction.GO_LEFT, BoardElement.GREEN_GOLD, BoardElement.NONE, BoardElement.NONE));
        assertFalse(GameBoard.canMove(LoderunnerAction.GO_LEFT, BoardElement.NONE, BoardElement.NONE, BoardElement.PIPE));
        assertFalse(GameBoard.canMove(LoderunnerAction.GO_UP, BoardElement.NONE, BoardElement.HERO_LEFT, BoardElement.BRICK));
        assertFalse(GameBoard.canMove(LoderunnerAction.GO_UP, BoardElement.GREEN_GOLD, BoardElement.HERO_LEFT, BoardElement.BRICK));
        assertFalse(GameBoard.canMove(LoderunnerAction.GO_RIGHT, BoardElement.GREEN_GOLD, BoardElement.NONE, BoardElement.HERO_LEFT));
    }
    @Test
    public void moveOnPipe() {
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_LEFT, BoardElement.PIPE, BoardElement.PIPE, BoardElement.NONE));
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_LEFT, BoardElement.PIPE, BoardElement.PIPE, BoardElement.NONE));

        assertTrue(GameBoard.canMove(LoderunnerAction.GO_DOWN, BoardElement.NONE, BoardElement.PIPE, BoardElement.NONE));

        assertTrue(GameBoard.canMove(LoderunnerAction.GO_DOWN, BoardElement.NONE, BoardElement.HERO_PIPE_LEFT, BoardElement.NONE));
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_DOWN, BoardElement.NONE, BoardElement.HERO_PIPE_RIGHT, BoardElement.NONE));

        assertFalse(GameBoard.canMove(LoderunnerAction.GO_RIGHT, BoardElement.NONE, BoardElement.NONE, BoardElement.PIPE));
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_RIGHT, BoardElement.NONE, BoardElement.PIPE, BoardElement.NONE));
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_RIGHT, BoardElement.GREEN_GOLD, BoardElement.PIPE, BoardElement.NONE));
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_RIGHT, BoardElement.NONE, BoardElement.HERO_PIPE_RIGHT, BoardElement.NONE));
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_RIGHT, BoardElement.GREEN_GOLD, BoardElement.HERO_PIPE_RIGHT, BoardElement.NONE));
        assertFalse(GameBoard.canMove(LoderunnerAction.GO_RIGHT, BoardElement.PIPE, BoardElement.HERO_RIGHT, BoardElement.NONE));
        assertTrue(GameBoard.canMove(LoderunnerAction.GO_RIGHT, BoardElement.PIPE, BoardElement.HERO_PIPE_RIGHT, BoardElement.NONE));
    }

}