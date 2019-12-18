package org.qwertech.loderunner;

import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.ansi;
import static org.qwertech.loderunner.api.BoardElement.BRICK;
import static org.qwertech.loderunner.api.BoardElement.GREEN_GOLD;
import static org.qwertech.loderunner.api.BoardElement.RED_GOLD;
import static org.qwertech.loderunner.api.BoardElement.UNDESTROYABLE_WALL;
import static org.qwertech.loderunner.api.BoardElement.YELLOW_GOLD;
import static org.qwertech.loderunner.api.BoardElement.heroes;
import static org.qwertech.loderunner.api.BoardElement.enemies;
import static org.qwertech.loderunner.api.BoardElement.otherHeroes;

import org.qwertech.loderunner.api.BoardElement;
import org.qwertech.loderunner.api.BoardPoint;
import org.qwertech.loderunner.api.GameBoard;
import org.qwertech.loderunner.api.LoderunnerAction;

import java.util.Arrays;
import java.util.List;

/**
 * PrintUtils.
 *
 * @author Pavel_Novikov
 */
public class PrintUtils {
    public static String[][] visitedToChars(String[][] background, int[][] visited) {
        int size = visited.length;
        String[][] chars = new String[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int point = visited[i][j];
                String back = background[i][j];
                chars[i][j] = point > 1 ? waveValueToString(point) : (back + back + back );
            }
        }
        return chars;
    }

    public static void print(String[][]... arrs) {
        System.out.println();
        for (int i = 0; i < arrs[0].length; i++) {
            for (int k = 0; k < arrs.length; k++) {
                String[][] arr = arrs[k];
                for (int j = 0; j < arr.length; j++) {
                    String boardItem = arr[j][i];
                    if (contains(boardItem, BRICK))
                        System.out.print(ansi().bgYellow().fgBrightYellow().a(boardItem).reset());
                    else if (contains(boardItem, UNDESTROYABLE_WALL)) {
                        System.out.print(ansi().bgYellow().a(boardItem).reset());
                    } else if (contains(boardItem, GREEN_GOLD)) {
                        System.out.print(ansi().bgGreen().a(boardItem).reset());
                    } else if (contains(boardItem, RED_GOLD)) {
                        System.out.print(ansi().bgRed().a(boardItem).reset());
                    } else if (contains(boardItem, YELLOW_GOLD)) {
                        System.out.print(ansi().bgBrightYellow().a(boardItem).reset());
                    } else if (heroes.stream().anyMatch(h -> contains(boardItem, h))) {
                        System.out.print(ansi().bgBrightYellow().fgMagenta().a(boardItem).reset());
                    } else if (otherHeroes.stream().anyMatch(h -> contains(boardItem, h))) {
                        System.out.print(ansi().fgBrightBlue().a(boardItem).reset());
                    } else if (enemies.stream().anyMatch(h -> contains(boardItem, h))) {
                        System.out.print(ansi().fgBrightRed().a(boardItem).reset());
                    } else if (Arrays.stream(LoderunnerAction.values()).anyMatch(h -> String.valueOf(h.getSymbol()).equals(boardItem))) {
                        System.out.print(ansi().bgBright(CYAN).a(boardItem).reset());
                    } else {
                        System.out.print(boardItem);
                    }

                }
                System.out.print("|");
            }
            System.out.println();
        }
    }

    private static boolean contains(String boardItem, BoardElement boardElement) {
        return boardItem.contains(String.valueOf(boardElement.getSymbol()));
    }

    private static String waveValueToString(Integer point) {
        return point<100?String.format(" %02d", point):" 99";
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String[][] actionsToSymbols(GameBoard gb, List<LoderunnerAction> actions) {
        String[][] path = gb.toArray();
        BoardPoint currPoint = actions.get(0).getTo(gb.getMyPosition()); //skip my current position
        int i = actions.get(0).getCost();
        while (i < actions.size()) {
            LoderunnerAction a = actions.get(i);
            path[currPoint.getX()][currPoint.getY()] = String.valueOf(a.getSymbol());
            currPoint = a.getTo(currPoint);
            i = i + a.getCost();
        }
        return path;
    }
}
