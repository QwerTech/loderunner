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
    private boolean[][] visited;

    public LoderunnerAction getMove() {
        BoardPoint myPosition = this.gb.getMyPosition();
        visited = new boolean[gb.size()][gb.size()];
        List<List<LoderunnerAction>> goldPaths = getGoldPaths(myPosition);


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
        return visited[boardPoint.getX()][boardPoint.getY()];
    }

    private boolean canMoveToAndNotVisited(BoardPoint from, LoderunnerAction action) {
        BoardPoint point = action.getTo(from);
        return gb.canMoveTo(from, action) && !isVisited(point);
    }

    private List<List<LoderunnerAction>> getGoldPaths(BoardPoint from) {
        visited[from.getX()][from.getY()] = true;
        return asList(getMoveWithAction(from, LoderunnerAction.GO_LEFT),
                getMoveWithAction(from, LoderunnerAction.GO_RIGHT),
                getMoveWithAction(from, LoderunnerAction.GO_DOWN),
                getMoveWithAction(from, LoderunnerAction.GO_UP)).stream()
                .filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());

    }

    private List<List<LoderunnerAction>> getMoveWithAction(BoardPoint from, LoderunnerAction action) {
        if (canMoveToAndNotVisited(from, action)) {
            BoardPoint point = action.getTo(from);
            if (gb.hasGoldAt(point)) {

                List<LoderunnerAction> path = new ArrayList<>(singletonList(action));
                return new ArrayList<>(singletonList(path));
            } else {
                List<List<LoderunnerAction>> movements = getGoldPaths(point);
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


}
