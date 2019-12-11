package org.qwertech.loderunner;

import static org.qwertech.loderunner.api.BoardElement.heros;

import org.qwertech.loderunner.api.BoardPoint;
import org.qwertech.loderunner.api.GameBoard;
import org.qwertech.loderunner.api.LoderunnerAction;

import java.util.List;

/**
 * PrintUtils.
 *
 * @author Pavel_Novikov
 */
public class PrintUtils {
    public static void print(boolean[][] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = arr[i].length - 1; j >= 0; j--) {

                System.out.print(arr[i][j] ? "+" : "-");
            }
            System.out.println();
        }
    }

    public static void print(GameBoard gb, boolean[][] visited) {
        char[][] arr = gb.toArray();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {

                char boardItem = arr[j][i];
                boolean player = heros.stream().anyMatch(h -> h.getSymbol() == boardItem);
                System.out.print(visited[j][i] && !player ? '+' : boardItem);
            }
            System.out.println();
        }
    }

    public static void print(GameBoard gb, List<LoderunnerAction> actions) {
        char[][] arr = gb.toArray();
        char[][] path = gb.toArray();
        BoardPoint currPoint = gb.getMyPosition();
        for (LoderunnerAction a : actions) {
            path[currPoint.getX()][currPoint.getY()] = a.getSymbol();
            currPoint = a.getTo(currPoint);
        }
        path[gb.getMyPosition().getX()][gb.getMyPosition().getY()] = '⚫';
        int h = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                //TODO
                char c = path[j][i];
                System.out.print(c != Character.MIN_VALUE ? c : arr[j][i]);
            }
            System.out.println();
        }
    }

    public static void print(GameBoard gb, List<LoderunnerAction> actions, boolean[][] visited) {
        char[][] arr = gb.toArray();
        char[][] path = gb.toArray();
        BoardPoint currPoint = gb.getMyPosition();
        for (LoderunnerAction a : actions) {
            path[currPoint.getX()][currPoint.getY()] = a.getSymbol();
            currPoint = a.getTo(currPoint);
        }
        path[gb.getMyPosition().getX()][gb.getMyPosition().getY()] = '⚫';
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                //TODO
                char c = path[j][i];

                char boardItem = arr[j][i];
                if (c != Character.MIN_VALUE) {
                    System.out.print(c);
                } else if (visited[j][i]) {
                    System.out.print('+');
                } else {
                    System.out.print(boardItem);
                }

            }
            System.out.println();
        }
    }

    public static void print(GameBoard gb) {
        char[][] arr = gb.toArray();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {

                System.out.print(arr[j][i]);
            }
            System.out.println();
        }
    }
}
