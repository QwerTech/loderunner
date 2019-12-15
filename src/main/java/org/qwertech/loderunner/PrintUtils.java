package org.qwertech.loderunner;

import org.qwertech.loderunner.api.BoardPoint;
import org.qwertech.loderunner.api.GameBoard;
import org.qwertech.loderunner.api.LoderunnerAction;

import java.util.Arrays;
import java.util.List;

import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.ansi;
import static org.qwertech.loderunner.api.BoardElement.*;

/**
 * PrintUtils.
 *
 * @author Pavel_Novikov
 */
public class PrintUtils {
    public static char[][] visitedToChars(char[][] background, int[][] visited) {
        int size = visited.length;
        char[][] chars = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int point = visited[i][j];
                chars[i][j] = point > 0 ? waveValueToString(point) : background[i][j];
            }
        }
        return chars;
    }

    public static void print(char[][]... arrs) {
        System.out.println();
        for (int i = 0; i < arrs[0].length; i++) {
            for (int k = 0; k < arrs.length; k++) {
                char[][] arr = arrs[k];
                for (int j = 0; j < arr.length; j++) {
                    char boardItem = arr[j][i];
                    if (BRICK.getSymbol() == boardItem)
                        System.out.print(ansi().bgYellow().fgBrightYellow().a(boardItem).reset());
                    else if (UNDESTROYABLE_WALL.getSymbol() == boardItem) {
                        System.out.print(ansi().bgYellow().a(boardItem).reset());
                    } else if (GREEN_GOLD.getSymbol() == boardItem) {
                        System.out.print(ansi().bgGreen().a(boardItem).reset());
                    } else if (RED_GOLD.getSymbol() == boardItem) {
                        System.out.print(ansi().bgRed().a(boardItem).reset());
                    } else if (YELLOW_GOLD.getSymbol() == boardItem) {
                        System.out.print(ansi().bgBrightYellow().a(boardItem).reset());
                    } else if (heros.stream().anyMatch(h -> h.getSymbol() == boardItem)) {
                        System.out.print(ansi().bgBrightYellow().fgMagenta().a(boardItem).reset());
                    } else if (otherHeros.stream().anyMatch(h -> h.getSymbol() == boardItem)) {
                        System.out.print(ansi().fgBrightBlue().a(boardItem).reset());
                    } else if (otherEnemy.stream().anyMatch(h -> h.getSymbol() == boardItem)) {
                        System.out.print(ansi().fgBrightRed().a(boardItem).reset());
                    } else if (Arrays.stream(LoderunnerAction.values()).anyMatch(h -> h.getSymbol() == boardItem)) {
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

    private static char waveValueToString(Integer point) {
        if (point == 0) {
            return ' ';
        }
        if (point < 33) {
            return '∙';
        } else if (point < 66) {
            return '◦';
        } else if (point < 80) {
            return '•';
        } else {
            return '♦';
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static char[][] actionsToSymbols(GameBoard gb, List<LoderunnerAction> actions) {
        char[][] path = gb.toArray();
        BoardPoint currPoint = actions.get(0).getTo(gb.getMyPosition()); //skip my current position

        for (int i = 1; i < actions.size(); i++) {
            LoderunnerAction a = actions.get(i);
            path[currPoint.getX()][currPoint.getY()] = a.getSymbol();
            currPoint = a.getTo(currPoint);
        }
        return path;
    }
}
