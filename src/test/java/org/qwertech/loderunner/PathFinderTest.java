package org.qwertech.loderunner;

import com.google.common.base.Charsets;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import org.qwertech.loderunner.api.GameBoard;
import org.qwertech.loderunner.api.LoderunnerAction;

import java.net.URL;
import java.util.List;

import static java.util.Arrays.asList;
import static org.qwertech.loderunner.api.LoderunnerAction.GO_LEFT;
import static org.qwertech.loderunner.api.LoderunnerAction.GO_UP;

public class PathFinderTest {
    @SneakyThrows
    public static String asString(String resource) {
        URL url = com.google.common.io.Resources.getResource(resource);
        return com.google.common.io.Resources.toString(url, Charsets.UTF_8);
    }

    @Test
    public void testGetPath1() {
        LoderunnerAction action = new PathFinder(new GameBoard(asString("board1"))).getMove();
        Assert.assertEquals(GO_UP, action);
    }
    @Test
    public void testGetPath2() {
        List<LoderunnerAction> path = new PathFinder(new GameBoard(asString("board2"))).getPath();
        Assert.assertEquals(asList(GO_LEFT, GO_LEFT), path);
    }
}