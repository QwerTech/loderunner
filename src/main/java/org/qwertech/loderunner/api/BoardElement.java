package org.qwertech.loderunner.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

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

    YELLOW_GOLD('$'),//1
    GREEN_GOLD('&'),//5
    RED_GOLD('@'),//10

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

    public static final Map<BoardElement, Integer> goldPrices = ImmutableMap.of(
            YELLOW_GOLD, 1,
            GREEN_GOLD, 3,
            RED_GOLD, 5);

    public static final List<BoardElement> heroes = ImmutableList.of(
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
    public static final List<BoardElement> otherHeroes = ImmutableList.of(
            OTHER_HERO_DIE,
            OTHER_HERO_LEFT,
            OTHER_HERO_RIGHT,
            OTHER_HERO_LADDER,
            OTHER_HERO_PIPE_LEFT,
            OTHER_HERO_PIPE_RIGHT
    );
    public static final List<BoardElement> enemies = ImmutableList.of(
            ENEMY_LADDER,
            ENEMY_LEFT,
            ENEMY_RIGHT,
            ENEMY_PIPE_LEFT,
            ENEMY_PIPE_RIGHT,
            ENEMY_PIT
    );
    public static final List<BoardElement> golds = ImmutableList.of(
            GREEN_GOLD,
            RED_GOLD,
            YELLOW_GOLD
    );

    public static final List<BoardElement> pits = ImmutableList.of(
            PIT_FILL_1,
            PIT_FILL_2,
            PIT_FILL_3,
            PIT_FILL_4,
            DRILL_PIT
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
        return heroes.contains(this);
    }

    public boolean isPit() {
        return pits.contains(this);
    }

    public boolean isBrick() {
        return BRICK.equals(this);
    }

    public boolean isEnemy() {
        return enemies.contains(this);
    }

    public boolean isOtherHero() {
        return otherHeroes.contains(this);
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
