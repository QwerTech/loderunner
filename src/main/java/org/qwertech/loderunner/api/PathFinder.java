package org.qwertech.loderunner.api;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PathFinder {

    private final GameBoard gb;
    private boolean[][] visited;

    public LoderunnerAction getMove() {
        BoardPoint myPosition = this.gb.getMyPosition();
        visited = new boolean[gb.size()][gb.size()];
        List<LoderunnerAction> goldPaths = getGoldPaths(myPosition);
        if (goldPaths == null) {
            log.warn("No path found.SUICIDE");
            return LoderunnerAction.GO_LEFT;
//            return LoderunnerAction.SUICIDE;
        }
        return goldPaths.get(goldPaths.size() - 1);
    }

    private boolean isVisited(BoardPoint boardPoint) {
        return visited[boardPoint.getX()][boardPoint.getY()];
    }

    private boolean canMoveToAndNotVisited(BoardPoint from, LoderunnerAction action) {
        BoardPoint point = gb.getTo(from, action);
        return gb.canMoveTo(from, action) && !isVisited(point);
    }

    private List<LoderunnerAction> getGoldPaths(BoardPoint from) {
        visited[from.getX()][from.getY()] = true;
        LoderunnerAction action = LoderunnerAction.GO_LEFT;

        if (canMoveToAndNotVisited(from, action)) {
            BoardPoint point = gb.getTo(from, action);
            if (gb.hasGoldAt(point)) {
                ArrayList<LoderunnerAction> movement = new ArrayList<>();
                movement.add(action);
                return movement;
            } else {
                List<LoderunnerAction> movement = getGoldPaths(point);
                if (movement == null) {
                    return null;
                } else {
                    movement.add(action);
                    return movement;
                }
            }
        }
        action = LoderunnerAction.GO_RIGHT;
        if (canMoveToAndNotVisited(from, action)) {
            BoardPoint point = gb.getTo(from, action);
            if (gb.hasGoldAt(point)) {
                ArrayList<LoderunnerAction> movement = new ArrayList<>();
                movement.add(action);
                return movement;
            } else {
                List<LoderunnerAction> movement = getGoldPaths(point);
                if (movement == null) {
                    return null;
                } else {
                    movement.add(action);
                    return movement;
                }
            }
        }
        action = LoderunnerAction.GO_DOWN;
        if (canMoveToAndNotVisited(from, action)) {
            BoardPoint point = gb.getTo(from, action);
            if (gb.hasGoldAt(point)) {
                ArrayList<LoderunnerAction> movement = new ArrayList<>();
                movement.add(action);
                return movement;
            } else {
                List<LoderunnerAction> movement = getGoldPaths(point);
                if (movement == null) {
                    return null;
                } else {
                    movement.add(action);
                    return movement;
                }
            }
        }
        action = LoderunnerAction.GO_UP;
        if (canMoveToAndNotVisited(from, action)) {
            BoardPoint point = gb.getTo(from, action);
            if (gb.hasGoldAt(point)) {
                ArrayList<LoderunnerAction> movement = new ArrayList<>();
                movement.add(action);
                return movement;
            } else {
                List<LoderunnerAction> movement = getGoldPaths(point);
                if (movement == null) {
                    return null;
                } else {
                    movement.add(action);
                    return movement;
                }
            }
        }

        return null;
    }


}
