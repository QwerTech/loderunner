package org.qwertech.loderunner;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.qwertech.loderunner.api.GameBoard;
import org.qwertech.loderunner.api.LoderunnerAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;
import static org.qwertech.loderunner.api.LoderunnerAction.*;

@Slf4j
public class Main {

    private static final String SERVER_ADDRESS = "http://eprumosw9005:8080/codenjoy-contest/board/player/up47ctoxlwznw9okpuqh?code=7626606218665225511";
    private static final List<LoderunnerAction> actions = asList(GO_LEFT, GO_RIGHT, GO_UP, GO_DOWN);
    private static final Random random = new Random();

    @SneakyThrows
    public static void main(String[] args) throws URISyntaxException, IOException {

        LodeRunnerClient client = new LodeRunnerClient(SERVER_ADDRESS);
        client.run(Main::getAuto);
//        client.run(Main::getManual);


        while (true) {
            Thread.sleep(100);
        }
//        System.in.read();

//        client.initiateExit();
    }

    public static boolean allSame(Collection<?> list) {
        Object fist = list.iterator().next();
        for (Object s : list) {
            if (!s.equals(fist)) {
                return false;
            }

        }
        return true;
    }

    private static LoderunnerAction getAuto(GameBoard gb) {
        MovesFromKilled.analyseMovesFromKilled(gb);
        LoderunnerAction move = new PathFinder(gb).getMove();
        History.writeMove(gb, move);
        new Statistics(gb).analyseMove();
        return History.stuckCorrect(move);
    }


    @SneakyThrows
    private static LoderunnerAction getManual(GameBoard gameBoard) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int key = br.read();
        switch (key) {
            case (int) 'w':
                return GO_UP;
            case (int) 's':
                return GO_DOWN;
            case (int) 'a':
                return GO_LEFT;
            case (int) 'd':
                return GO_RIGHT;
            case (int) 'e':
                return DRILL_RIGHT;
            case (int) 'q':
                return DRILL_LEFT;

        }
        return actions
                .get(random.nextInt(actions.size()));
    }
}