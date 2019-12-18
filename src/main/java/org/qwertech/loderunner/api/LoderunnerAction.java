package org.qwertech.loderunner.api;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.Arrays.asList;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter

public enum LoderunnerAction {
    GO_LEFT('←'),
    GO_RIGHT('→'),
    GO_UP('↑'),
    GO_DOWN('↓'),
    DRILL_RIGHT('⤥'),
    DRILL_LEFT('⤦'),
    DO_NOTHING('.'),
    SUICIDE('x');

    private final char symbol;

    public static List<LoderunnerAction> moveActions() {
        return asList(GO_LEFT, GO_RIGHT, GO_UP, GO_DOWN, DRILL_RIGHT, DRILL_LEFT);
    }

    public boolean left() {
        return this.equals(GO_LEFT);
    }

    public boolean up() {
        return this.equals(GO_UP);
    }

    public boolean down() {
        return this.equals(GO_DOWN);
    }

    public boolean right() {
        return this.equals(GO_RIGHT);
    }

    public boolean vertical() {
        return up() || down();
    }

    public boolean horizontal() {
        return right() || left();
    }

    public boolean drill() {
        return this.equals(DRILL_LEFT) || this.equals(DRILL_RIGHT);
    }

    public BoardPoint getTo(BoardPoint from) {
        BoardPoint to;
        if (this.equals(GO_LEFT)) {
            to = from.shiftLeft();
        } else if (this.equals(GO_RIGHT)) {
            to = from.shiftRight();
        } else if (this.equals(GO_UP)) {
            to = from.shiftTop();
        } else if (this.equals(GO_DOWN)) {
            to = from.shiftBottom();
        } else if (this.equals(DRILL_RIGHT)) {
            to = from.shiftBottom().shiftRight();
        } else if (this.equals(DRILL_LEFT)) {
            to = from.shiftBottom().shiftLeft();
        } else {
            throw new IllegalStateException();
        }
        return to;
    }

    private static final Map<LoderunnerAction, List<LoderunnerAction>> complexActions =
            of(DRILL_RIGHT, ImmutableList.of(DRILL_RIGHT, GO_RIGHT, GO_DOWN),
                    DRILL_LEFT, ImmutableList.of(DRILL_LEFT, GO_LEFT, GO_DOWN));

    public Integer getCost() {
        return complexActions.getOrDefault(this, Collections.singletonList(this)).size();
    }

    public List<LoderunnerAction> unwrapComplexAction() {
        return complexActions.getOrDefault(this, Collections.singletonList(this));
    }

    public BoardPoint getFrom(BoardPoint to) {
        BoardPoint from;
        if (this.equals(GO_LEFT)) {
            from = to.shiftRight();
        } else if (this.equals(GO_RIGHT)) {
            from = to.shiftLeft();
        } else if (this.equals(GO_UP)) {
            from = to.shiftBottom();
        } else if (this.equals(GO_DOWN)) {
            from = to.shiftTop();
        } else if (this.equals(DRILL_RIGHT)) {
            from = to.shiftTop().shiftLeft();
        } else if (this.equals(DRILL_LEFT)) {
            from = to.shiftTop().shiftRight();
        } else {
            throw new IllegalStateException();
        }
        return from;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
