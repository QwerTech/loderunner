package org.qwertech.loderunner;

import static java.util.Arrays.asList;
import static org.qwertech.loderunner.api.LoderunnerAction.DRILL_LEFT;
import static org.qwertech.loderunner.api.LoderunnerAction.DRILL_RIGHT;
import static org.qwertech.loderunner.api.LoderunnerAction.GO_DOWN;
import static org.qwertech.loderunner.api.LoderunnerAction.GO_LEFT;
import static org.qwertech.loderunner.api.LoderunnerAction.GO_RIGHT;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import org.qwertech.loderunner.api.GameBoard;
import org.qwertech.loderunner.api.LoderunnerAction;

import java.net.URL;
import java.util.List;

public class PathFinderTest {
    @SneakyThrows
    public static String asString(String resource) {
        URL url = Resources.getResource(resource);
        return Resources.toString(url, Charsets.UTF_8);
    }

    @Test
    public void testGetPath1() {
        LoderunnerAction action = new PathFinder(new GameBoard(asString("board1"))).getMove();
        Assert.assertEquals(DRILL_RIGHT, action);
    }

    @Test
    public void testGetPath2() {
        List<LoderunnerAction> path = new PathFinder(new GameBoard(asString("board2"))).getPath();
        Assert.assertEquals(asList(GO_LEFT, GO_LEFT), path);
    }

    @Test
    public void testGetPath3Drill() {
        List<LoderunnerAction> path = new PathFinder(new GameBoard(asString("board3"))).getPath();
        Assert.assertEquals(asList(DRILL_LEFT, GO_LEFT, GO_DOWN, GO_DOWN), path);
    }

    @Test
    public void testGetPath4Drill() {
        List<LoderunnerAction> path = new PathFinder(new GameBoard(asString("board4"))).getPath();
        Assert.assertEquals(12, path.size());
    }

    @Test
    public void testGetPath5Drill() {
        List<LoderunnerAction> path = new PathFinder(new GameBoard(asString("board5"))).getPath();
        //[⤥, →, ↓, ↓, ↓,
        // ⤥, →, ↓, ↓,
        // ⤦, ←, ↓, ↓, ←, ←, ←,
        // ⤦, ←, ↓, ↓]
        List<LoderunnerAction> expected = asList(
                DRILL_RIGHT, GO_RIGHT, GO_DOWN, GO_DOWN, GO_DOWN,
                DRILL_RIGHT, GO_RIGHT, GO_DOWN, GO_DOWN,
                DRILL_LEFT, GO_LEFT, GO_DOWN, GO_DOWN, GO_LEFT, GO_LEFT, GO_LEFT,
                DRILL_LEFT, GO_LEFT, GO_DOWN, GO_DOWN);
        Assert.assertEquals(expected, path);
    }
}