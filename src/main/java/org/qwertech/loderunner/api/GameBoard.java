package org.qwertech.loderunner.api;


import static org.qwertech.loderunner.api.BoardElement.HERO_PIPE_LEFT;
import static org.qwertech.loderunner.api.BoardElement.HERO_PIPE_RIGHT;
import static org.qwertech.loderunner.api.BoardElement.HERO_SHADOW_DRILL_LEFT;
import static org.qwertech.loderunner.api.BoardElement.HERO_SHADOW_DRILL_RIGHT;
import static org.qwertech.loderunner.api.BoardElement.LADDER;
import static org.qwertech.loderunner.api.BoardElement.NONE;
import static org.qwertech.loderunner.api.BoardElement.PIPE;
import static org.qwertech.loderunner.api.BoardElement.UNDESTROYABLE_WALL;
import static org.qwertech.loderunner.api.LoderunnerAction.GO_DOWN;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBoard {
    @Getter
    private String boardString;

    public GameBoard(String boardString) {
        this.boardString = boardString.replace("\n", "");
    }

    public int size() {
        return (int) Math.sqrt(boardString.length());
    }

    public BoardPoint getMyPosition() {
        List<BoardPoint> result = findAllElements(BoardElement.HERO_DIE);
        result.addAll(findAllElements(BoardElement.HERO_DRILL_LEFT));
        result.addAll(findAllElements(BoardElement.HERO_DRILL_RIGHT));
        result.addAll(findAllElements(BoardElement.HERO_FALL_LEFT));
        result.addAll(findAllElements(BoardElement.HERO_FALL_RIGHT));
        result.addAll(findAllElements(BoardElement.HERO_LADDER));
        result.addAll(findAllElements(BoardElement.HERO_PIPE_LEFT));
        result.addAll(findAllElements(BoardElement.HERO_PIPE_RIGHT));
        result.addAll(findAllElements(BoardElement.HERO_LEFT));
        result.addAll(findAllElements(BoardElement.HERO_RIGHT));
        result.addAll(findAllElements(HERO_SHADOW_DRILL_LEFT));
        result.addAll(findAllElements(HERO_SHADOW_DRILL_RIGHT));
        result.addAll(findAllElements(BoardElement.HERO_SHADOW_LADDER));
        result.addAll(findAllElements(BoardElement.HERO_SHADOW_LEFT));
        result.addAll(findAllElements(BoardElement.HERO_SHADOW_RIGHT));
        result.addAll(findAllElements(BoardElement.HERO_SHADOW_FALL_LEFT));
        result.addAll(findAllElements(BoardElement.HERO_SHADOW_FALL_RIGHT));
        result.addAll(findAllElements(BoardElement.HERO_SHADOW_PIPE_LEFT));
        result.addAll(findAllElements(BoardElement.HERO_SHADOW_PIPE_RIGHT));
        return result.get(0);
    }

    public boolean isGameOver() {
        return boardString.contains("" + BoardElement.HERO_DIE.getSymbol());
    }

    public boolean hasElementAt(BoardPoint point, BoardElement element) {
        if (point.isOutOfBoard(size())) {
            return false;
        }

        return getElementAt(point) == element;
    }

    public BoardElement getElementAt(BoardPoint point) {
        return BoardElement.valueOf(boardString.charAt(getShiftByPoint(point)));
    }

    public void printBoard() {
        for (int i = 0; i < size(); i++) {
            System.out.println(boardString.substring(i * size(), size() * (i + 1)));
        }
    }

    public List<BoardPoint> findAllElements(BoardElement element) {
        List<BoardPoint> result = new ArrayList<>();

        for (int i = 0; i < size() * size(); i++) {
            BoardPoint pt = getPointByShift(i);

            if (hasElementAt(pt, element)) {
                result.add(pt);
            }
        }

        return result;
    }

    public List<BoardPoint> getEnemyPositions() {
        List<BoardPoint> result = findAllElements(BoardElement.ENEMY_LADDER);

        result.addAll(findAllElements(BoardElement.ENEMY_LEFT));
        result.addAll(findAllElements(BoardElement.ENEMY_PIPE_LEFT));
        result.addAll(findAllElements(BoardElement.ENEMY_PIPE_RIGHT));
        result.addAll(findAllElements(BoardElement.ENEMY_RIGHT));
        result.addAll(findAllElements(BoardElement.ENEMY_PIT));
        return result;
    }

    public List<BoardPoint> getOtherHeroPositions() {
        List<BoardPoint> result = findAllElements(BoardElement.OTHER_HERO_LADDER);

        result.addAll(findAllElements(BoardElement.OTHER_HERO_LEFT));
        result.addAll(findAllElements(BoardElement.OTHER_HERO_PIPE_LEFT));
        result.addAll(findAllElements(BoardElement.OTHER_HERO_PIPE_RIGHT));
        result.addAll(findAllElements(BoardElement.OTHER_HERO_RIGHT));
        result.addAll(findAllElements(BoardElement.OTHER_HERO_LADDER));
        result.addAll(findAllElements(BoardElement.OTHER_HERO_SHADOW_LEFT));
        result.addAll(findAllElements(BoardElement.OTHER_HERO_SHADOW_RIGHT));
        result.addAll(findAllElements(BoardElement.OTHER_HERO_SHADOW_LADDER));
        result.addAll(findAllElements(BoardElement.OTHER_HERO_SHADOW_PIPE_LEFT));
        result.addAll(findAllElements(BoardElement.OTHER_HERO_SHADOW_PIPE_RIGHT));
        return result;
    }

    public List<BoardPoint> getShadowPills() {
        return findAllElements(BoardElement.THE_SHADOW_PILL);
    }

    public List<BoardPoint> getPortals() {
        return findAllElements(BoardElement.PORTAL);
    }

    public List<BoardPoint> getWallPositions() {
        List<BoardPoint> result = findAllElements(BoardElement.BRICK);
        result.addAll(findAllElements(BoardElement.UNDESTROYABLE_WALL));
        return result;
    }

    public List<BoardPoint> getLadderPositions() {
        List<BoardPoint> result = findAllElements(BoardElement.LADDER);
        result.addAll(findAllElements(BoardElement.HERO_LADDER));
        result.addAll(findAllElements(BoardElement.OTHER_HERO_LADDER));
        result.addAll(findAllElements(BoardElement.ENEMY_LADDER));
        return result;
    }

    public List<BoardPoint> getGoldPositions() {
        List<BoardPoint> result = findAllElements(BoardElement.YELLOW_GOLD);
        result.addAll(findAllElements(BoardElement.GREEN_GOLD));
        result.addAll(findAllElements(BoardElement.RED_GOLD));
        return result;
    }

    public List<BoardPoint> getPipePositions() {
        List<BoardPoint> result = findAllElements(BoardElement.PIPE);

        result.addAll(findAllElements(BoardElement.HERO_PIPE_LEFT));
        result.addAll(findAllElements(BoardElement.HERO_PIPE_RIGHT));
        result.addAll(findAllElements(BoardElement.OTHER_HERO_PIPE_LEFT));
        result.addAll(findAllElements(BoardElement.OTHER_HERO_PIPE_RIGHT));
        result.addAll(findAllElements(BoardElement.ENEMY_PIPE_LEFT));
        result.addAll(findAllElements(BoardElement.ENEMY_PIPE_RIGHT));
        return result;
    }

    public boolean hasElementAt(BoardPoint point, BoardElement... elements) {
        return Arrays.stream(elements).anyMatch(element -> hasElementAt(point, element));
    }

    public boolean isNearToElement(BoardPoint point, BoardElement element) {
        if (point.isOutOfBoard(size()))
            return false;

        return hasElementAt(point.shiftBottom(), element)
                || hasElementAt(point.shiftTop(), element)
                || hasElementAt(point.shiftLeft(), element)
                || hasElementAt(point.shiftRight(), element);
    }

    public boolean hasEnemyAt(BoardPoint point) {
        return getEnemyPositions().contains(point);
    }

    public boolean hasOtherHeroAt(BoardPoint point) {
        return getOtherHeroPositions().contains(point);
    }

    public boolean hasWallAt(BoardPoint point) {
        return getWallPositions().contains(point);
    }

    public boolean hasLadderAt(BoardPoint point) {
        return getLadderPositions().contains(point);
    }

    public boolean hasGoldAt(BoardPoint point) {
        return getGoldPositions().contains(point);
    }

    public boolean hasPipeAt(BoardPoint point) {
        return getPipePositions().contains(point);
    }

    public boolean isShadow(BoardPoint point) {
        return getShadows().contains(point);
    }

    public boolean isPortal(BoardPoint point) {
        return getPortals().contains(point);
    }

    private List<BoardPoint> getShadows() {
        List<BoardPoint> shadows = findAllElements(HERO_SHADOW_DRILL_LEFT);
        shadows.addAll(findAllElements(HERO_SHADOW_DRILL_RIGHT));
        shadows.addAll(findAllElements(BoardElement.HERO_SHADOW_LADDER));
        shadows.addAll(findAllElements(BoardElement.HERO_SHADOW_LEFT));
        shadows.addAll(findAllElements(BoardElement.HERO_SHADOW_RIGHT));
        shadows.addAll(findAllElements(BoardElement.HERO_SHADOW_FALL_LEFT));
        shadows.addAll(findAllElements(BoardElement.HERO_SHADOW_FALL_RIGHT));
        shadows.addAll(findAllElements(BoardElement.HERO_SHADOW_PIPE_LEFT));
        shadows.addAll(findAllElements(BoardElement.HERO_SHADOW_PIPE_RIGHT));

        shadows.addAll(findAllElements(BoardElement.OTHER_HERO_SHADOW_LEFT));
        shadows.addAll(findAllElements(BoardElement.OTHER_HERO_SHADOW_RIGHT));
        shadows.addAll(findAllElements(BoardElement.OTHER_HERO_SHADOW_LADDER));
        shadows.addAll(findAllElements(BoardElement.OTHER_HERO_SHADOW_PIPE_LEFT));
        shadows.addAll(findAllElements(BoardElement.OTHER_HERO_SHADOW_PIPE_RIGHT));
        return shadows;
    }

    public int getCountElementsNearToPoint(BoardPoint point, BoardElement element) {
        if (point.isOutOfBoard(size()))
            return 0;

        return boolToInt(hasElementAt(point.shiftLeft(), element)) +
                boolToInt(hasElementAt(point.shiftRight(), element)) +
                boolToInt(hasElementAt(point.shiftTop(), element)) +
                boolToInt(hasElementAt(point.shiftBottom(), element));
    }

    private int getShiftByPoint(BoardPoint point) {
        return point.getY() * size() + point.getX();
    }

    private BoardPoint getPointByShift(int shift) {
        return new BoardPoint(shift % size(), shift / size());
    }

    private int boolToInt(boolean bool) {
        return bool ? 1 : 0;
    }

    public boolean canMoveTo(BoardPoint from, LoderunnerAction action) {

        BoardPoint under = from.shiftBottom();
        BoardElement underElement = getElementAt(under);

        BoardPoint to = action.getTo(from);
        BoardElement toElement = getElementAt(to);
        BoardElement fromElement = getElementAt(from);

        return canMove(action, toElement, fromElement, underElement);
    }

    protected static boolean canMove(LoderunnerAction action, BoardElement toElement, BoardElement fromElement, BoardElement underElement) {
        if ((underElement.isNoneOrGold() || underElement.isPipe()) && fromElement.isNoneOrGold()) {
            return action.down();
        }

        if (toElement.equals(UNDESTROYABLE_WALL)) {
            return false;
        }

        if (toElement.isNoneOrGold()) {
            if (action.horizontal()) {
                return true;
            }
            if ((fromElement.isLadder()) && action.equals(GO_DOWN)) {
                return true;
            }
            if (fromElement.isLadder() && (action.up() || action.horizontal())) {
                return true;
            }
            if (fromElement.equals(NONE) && action.down()) {
                return true;
            }
        }
        if (toElement.equals(LADDER)) {
            if ((fromElement.isLadder()) && action.vertical()) {
                return true;
            }
            if ((fromElement.isNoneOrGold() || fromElement.isHero()) && action.horizontal()) {
                return true;
            }
        }
        if (toElement.equals(PIPE)) {
            if (action.horizontal() || action.down()) {
                return true;
            }
        }
        if (fromElement.equals(PIPE) || fromElement.equals(HERO_PIPE_LEFT) || fromElement.equals(HERO_PIPE_RIGHT)) {
            if (action.horizontal() && toElement.equals(PIPE)) {
                return true;
            }
            if (toElement.isNoneOrGold() && action.down()) {
                return true;
            }
        }
        return false;
    }


    public char[][] toArray() {
        char[][] chars = new char[size()][size()];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                chars[i][j] = getElementAt(new BoardPoint(i, j)).getSymbol();
            }
        }
        return chars;
    }
}
