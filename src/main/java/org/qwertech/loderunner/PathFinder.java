package org.qwertech.loderunner;


import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qwertech.loderunner.api.BoardPoint;
import org.qwertech.loderunner.api.GameBoard;
import org.qwertech.loderunner.api.LoderunnerAction;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.qwertech.loderunner.api.LoderunnerAction.*;

@Slf4j
@RequiredArgsConstructor
public class PathFinder {

    private final GameBoard gb;
    private int[][] visited;

    public LoderunnerAction getMove() {
        BoardPoint myPosition = this.gb.getMyPosition();
        visited = new int[gb.size()][gb.size()];
        wavePropagation();
        PrintUtils.print(gb.toArray(), PrintUtils.visitedToChars(gb.toArray(), visited));
        List<LoderunnerAction> path = recoverPath();
//        List<List<LoderunnerAction>> goldPaths = getGoldPaths(myPosition, 1);

//
        if (path.isEmpty()) {
//            log.warn("No path found.SUICIDE");
//            return LoderunnerAction.GO_LEFT;
            List<LoderunnerAction> randomActions = asList(DRILL_LEFT, DRILL_RIGHT, GO_LEFT, GO_RIGHT);
            return randomActions.get(new Random().nextInt(randomActions.size()));
//            return DO_NOTHING;
        }
//        List<LoderunnerAction> path;
//        if (goldPaths.size() == 1) {
//            path = goldPaths.get(0);
//        } else {
//            path = goldPaths.stream().min(Comparator.comparing(List::size)).orElse(null);
//        }
        Collections.reverse(path);
        System.out.println();
        System.out.println();
        PrintUtils.print(gb, path, visited);
        System.out.print(path.size() + "     ");
        System.out.print(path);

        return path.get(0);
    }


    private boolean isVisited(BoardPoint boardPoint) {
        return getVisitedValue(boardPoint) != 0;
    }

    private boolean canMoveToAndNotVisited(BoardPoint from, LoderunnerAction action) {
        BoardPoint point = action.getTo(from);
        return gb.canMoveTo(from, action) && !isVisited(point);
    }

    private List<List<LoderunnerAction>> getGoldPaths(BoardPoint from, int length) {
        visited[from.getX()][from.getY()] = length;
        return asList(getMoveWithAction(from, GO_LEFT, length),
                getMoveWithAction(from, GO_RIGHT, length),
                getMoveWithAction(from, GO_DOWN, length),
                getMoveWithAction(from, GO_UP, length)).stream()
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
        Optional<Pair<BoardPoint, Integer>> gold = gb.getGoldPositions().stream()
                .map(a -> new Pair<>(a, getVisitedValue(a)))
                .filter(p -> p.getValue() != 0)
                .min(Comparator.comparing(Pair::getValue));
        if(!gold.isPresent()){
            return emptyList();
        }
        BoardPoint closestGold = gold.get().getKey();

        List<LoderunnerAction> path = new ArrayList<>();      // массив, где хранится путь
        BoardPoint currentNode = closestGold;
        while (currentNode.notEquals(gb.getMyPosition())) {                     // пока не дошли до стартового узла
            BoardPoint currentNode1 = currentNode;
            LoderunnerAction action = moveActions().stream()
                    .map(a -> new Pair<>(a, getVisitedValue(a.getFrom(currentNode1))))
                    .filter(p -> p.getValue() != 0)
                    .min(Comparator.comparing(Pair::getValue))// проверяем соседние узлы
                    .orElseThrow(IllegalStateException::new).getKey();
            path.add(action);// заносится в массив
            currentNode = action.getFrom(currentNode);//узел становится текущим
        }
        return path;
    }

    public void wavePropagation() {  // распространение волны
        visited = new int[gb.size()][gb.size()]; // массив, где будут хранится "отметки" каждого узла
        int steps = gb.size() * gb.size();
        int markNumber = 1;                        // счетчик
        setVisitedValue(gb.getMyPosition(), markNumber);         // инициализация стартового узла
        while (steps > 0) {          // пока не достигли финишного узла
            for (int i = 0; i < visited.length; i++) {
                for (int j = 0; j < visited.length; j++) {
                    if (visited[i][j] == markNumber) {                                          // начинаем со стартового узла
                        BoardPoint from = new BoardPoint(i, j);
                        int markNumber1 = markNumber;
                        moveActions().forEach(a -> checkPoint(markNumber1, from, a));
                    }
                }
            }
            markNumber++;
            steps--;
        }
    }

    private int getVisitedValue(BoardPoint myPosition) {
        return visited[myPosition.getX()][myPosition.getY()];
    }

    private void setVisitedValue(BoardPoint myPosition, int value) {
        visited[myPosition.getX()][myPosition.getY()] = value;
    }

    private void checkPoint(int markNumber, BoardPoint from, LoderunnerAction action) {
        BoardPoint to = action.getTo(from);
        if (gb.canMoveTo(from, action) && getVisitedValue(to) == 0) {
            visited[to.getX()][to.getY()] = markNumber + 1;
        }
    }
}
