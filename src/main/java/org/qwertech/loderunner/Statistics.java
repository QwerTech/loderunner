package org.qwertech.loderunner;

import lombok.extern.slf4j.Slf4j;
import org.qwertech.loderunner.api.BoardElement;
import org.qwertech.loderunner.api.BoardPoint;
import org.qwertech.loderunner.api.GameBoard;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Statistics {
    private static final List<BoardElement> gatheredGoldFromKilled = new ArrayList<>();

    static {
        History.addKilledListener(gatheredGoldFromKilled::clear);
    }

    private GameBoard gb;

    public Statistics(GameBoard gb) {
        this.gb = gb;
    }

    public static int getGatheredGoldCountFromKilled() {
        return gatheredGoldFromKilled.size();
    }

    public void analyseMove() {
        GameBoard prevGb = History.getPrevBoard();
        BoardPoint myPosition = gb.getMyPosition();
        prevGb.getGoldPositions().stream()
                .filter(g -> g.equals(myPosition))
                .findFirst()
                .ifPresent(boardPoint -> gatheredGoldFromKilled.add(gb.getElementAt(boardPoint)));
    }
}
