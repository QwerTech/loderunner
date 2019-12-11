package org.qwertech.loderunner.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LoderunnerAction {
    GO_LEFT('←'),
    GO_RIGHT('→'),
    GO_UP('↑'),
    GO_DOWN('↓'),
    DRILL_RIGHT('⇝'),
    DRILL_LEFT('⇜'),
    DO_NOTHING('.'),
    SUICIDE('x');

    private final char symbol;

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
        } else {
            throw new IllegalStateException();
        }
        return to;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
