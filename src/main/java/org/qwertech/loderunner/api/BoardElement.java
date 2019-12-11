package org.qwertech.loderunner.api;

import static java.util.Arrays.asList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public enum BoardElement {
    NONE(' '),

    BRICK('#'),
    PIT_FILL_1('1'),
    PIT_FILL_2('2'),
    PIT_FILL_3('3'),
    PIT_FILL_4('4'),
    UNDESTROYABLE_WALL('☼'),

    DRILL_PIT('*'),

    ENEMY_LADDER('Q'),
    ENEMY_LEFT('«'),
    ENEMY_RIGHT('»'),
    ENEMY_PIPE_LEFT('<'),
    ENEMY_PIPE_RIGHT('>'),
    ENEMY_PIT('X'),

    YELLOW_GOLD('$'),
    GREEN_GOLD('&'),
    RED_GOLD('@'),

    HERO_DIE('Ѡ'),
    HERO_DRILL_LEFT('Я'),
    HERO_DRILL_RIGHT('R'),
    HERO_LADDER('Y'),
    HERO_LEFT('◄'),
    HERO_RIGHT('►'),
    HERO_FALL_LEFT(']'),
    HERO_FALL_RIGHT('['),
    HERO_PIPE_LEFT('{'),
    HERO_PIPE_RIGHT('}'),

    HERO_SHADOW_DRILL_LEFT('⊰'),
    HERO_SHADOW_DRILL_RIGHT('⊱'),
    HERO_SHADOW_LADDER('⍬'),
    HERO_SHADOW_LEFT('⊲'),
    HERO_SHADOW_RIGHT('⊳'),
    HERO_SHADOW_FALL_LEFT('⊅'),
    HERO_SHADOW_FALL_RIGHT('⊄'),
    HERO_SHADOW_PIPE_LEFT('⋜'),
    HERO_SHADOW_PIPE_RIGHT('⋝'),

    OTHER_HERO_DIE('Z'),
    OTHER_HERO_LEFT(')'),
    OTHER_HERO_RIGHT('('),
    OTHER_HERO_LADDER('U'),
    OTHER_HERO_PIPE_LEFT('Э'),
    OTHER_HERO_PIPE_RIGHT('Є'),

    OTHER_HERO_SHADOW_DIE('⋈'),
    OTHER_HERO_SHADOW_LEFT('⋊'),
    OTHER_HERO_SHADOW_RIGHT('⋉'),
    OTHER_HERO_SHADOW_LADDER('⋕'),
    OTHER_HERO_SHADOW_PIPE_LEFT('⊣'),
    OTHER_HERO_SHADOW_PIPE_RIGHT('⊢'),

    LADDER('H'),
    PIPE('~'),

    PORTAL('⊛'),

    THE_SHADOW_PILL('S');

    public static List<BoardElement> heros = asList(
            HERO_DIE,
            HERO_DRILL_LEFT,
            HERO_DRILL_RIGHT,
            HERO_LADDER,
            HERO_LEFT,
            HERO_RIGHT,
            HERO_FALL_LEFT,
            HERO_FALL_RIGHT,
            HERO_PIPE_LEFT,
            HERO_PIPE_RIGHT
    );
    public static List<BoardElement> golds = asList(
            GREEN_GOLD,
            RED_GOLD,
            YELLOW_GOLD
    );
    final char symbol;

    public static BoardElement valueOf(char ch) {
        for (BoardElement el : BoardElement.values()) {
            if (el.symbol == ch) {
                return el;
            }
        }
        throw new IllegalArgumentException("No such element for " + ch);
    }

    public boolean isHero() {
        return heros.contains(this);
    }

    public boolean isLadder() {
        return this.equals(LADDER) || this.equals(HERO_LADDER);
    }

    public boolean isPipe() {
        return this.equals(PIPE) || this.equals(HERO_PIPE_LEFT) || this.equals(HERO_PIPE_RIGHT);
    }

    public boolean isGold() {
        return golds.contains(this);
    }

    public boolean isNoneOrGold() {
        return this.equals(NONE) || isGold();
    }


//    @Override
//    public String toString() {
//        return symbol;
//    }
}
