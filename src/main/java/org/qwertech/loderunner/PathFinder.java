package org.qwertech.loderunner;


import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qwertech.loderunner.api.BoardPoint;
import org.qwertech.loderunner.api.GameBoard;
import org.qwertech.loderunner.api.LoderunnerAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class PathFinder {

    private final GameBoard gb;
    private int[][] visited;

    public LoderunnerAction getMove() {
        BoardPoint myPosition = this.gb.getMyPosition();
        visited = new int[gb.size()][gb.size()];
        List<List<LoderunnerAction>> goldPaths = getGoldPaths(myPosition, 1);


        if (goldPaths.isEmpty()) {
//            log.warn("No path found.SUICIDE");
//            return LoderunnerAction.GO_LEFT;
            return LoderunnerAction.SUICIDE;
        }
        List<LoderunnerAction> path;
        if (goldPaths.size() == 1) {
            path = goldPaths.get(0);
        } else {
            path = goldPaths.stream().min(Comparator.comparing(List::size)).orElse(null);
        }
        Collections.reverse(path);
        System.out.println();
        System.out.println();
        PrintUtils.print(gb, path, visited);
        System.out.print(path.size() + "     ");
        System.out.print(path);

        return path.get(0);
    }


    private boolean isVisited(BoardPoint boardPoint) {
        return visited[boardPoint.getX()][boardPoint.getY()] != 0;
    }

    private boolean canMoveToAndNotVisited(BoardPoint from, LoderunnerAction action) {
        BoardPoint point = action.getTo(from);
        return gb.canMoveTo(from, action) && !isVisited(point);
    }

    private List<List<LoderunnerAction>> getGoldPaths(BoardPoint from, int length) {
        visited[from.getX()][from.getY()] = length;
        return asList(getMoveWithAction(from, LoderunnerAction.GO_LEFT, length),
                getMoveWithAction(from, LoderunnerAction.GO_RIGHT, length),
                getMoveWithAction(from, LoderunnerAction.GO_DOWN, length),
                getMoveWithAction(from, LoderunnerAction.GO_UP, length)).stream()
                .filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());

    }

    private List<List<LoderunnerAction>> getMoveWithAction(BoardPoint from, LoderunnerAction action, int length) {
        if (canMoveToAndNotVisited(from, action)) {
            BoardPoint point = action.getTo(from);
            if (gb.hasGoldAt(point)) {

                List<LoderunnerAction> path = new ArrayList<>(singletonList(action));
                return new ArrayList<>(singletonList(path));
            } else {
                List<List<LoderunnerAction>> movements = getGoldPaths(point, ++length);
                if (movements == null) {
                    return null;
                } else {
                    movements.forEach(m -> m.add(action));
                    return movements;
                }
            }
        }
        return null;
    }

    private List<List<LoderunnerAction>> fillVisited(BoardPoint from, LoderunnerAction action, int length) {
        if (canMoveToAndNotVisited(from, action)) {
            BoardPoint point = action.getTo(from);
            List<List<LoderunnerAction>> movements = getGoldPaths(point, ++length);
            if (movements == null) {
                return null;
            } else {
                movements.forEach(m -> m.add(action));
                return movements;
            }

        }
        return null;
    }

    public List<LoderunnerAction> recoverPath() {
        gb.getGoldPositions().stream().min(Comparator.comparing(g -> visited[g.getX()][g.getY()]))
        .ifPresent(g->{

        });
    }

    public void wavePropagation() {  // распространение волны
        visited = new int[gb.size()][gb.size()]; // массив, где будут хранится "отметки" каждого узла
        int steps = gb.size() * gb.size();
        int markNumber = 1;                        // счетчик
        visited[gb.getMyPosition().getX()][gb.getMyPosition().getY()] = markNumber;         // инициализация стартового узла
        while (steps > 0) {          // пока не достигли финишного узла
            for (int i = 0; i < visited.length; i++) {
                for (int j = 0; j < visited.length; j++) {
                    if (visited[i][j] == markNumber) {                                          // начинаем со стартового узла
                        BoardPoint from = new BoardPoint(i, j);
                        checkPoint(markNumber, from, LoderunnerAction.GO_LEFT);
                        checkPoint(markNumber, from, LoderunnerAction.GO_RIGHT);
                        checkPoint(markNumber, from, LoderunnerAction.GO_DOWN);
                        checkPoint(markNumber, from, LoderunnerAction.GO_UP);
                    }
                }
            }
            markNumber++;
            steps--;
        }
    }

    private void checkPoint(int markNumber, BoardPoint from, LoderunnerAction action) {
        BoardPoint to = action.getTo(from);
        if (gb.canMoveTo(from, action) && visited[to.getX()][to.getY()] == 0) {
            visited[to.getX()][to.getY()] = markNumber + 1;
        }
    }
}
