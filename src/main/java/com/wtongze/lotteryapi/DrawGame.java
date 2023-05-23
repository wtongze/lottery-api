package com.wtongze.lotteryapi;

import java.util.Arrays;
import java.util.Optional;

public enum DrawGame {
    POWER_BALL("powerball"),
    SUPER_LOTTO_PLUS("superlottoplus"),
    FANTASY_5("fantasy5");

    private final String label;

    public static Optional<DrawGame> fromString(String val) {
        return Arrays.stream(DrawGame.values()).filter(i -> i.label.equals(val)).findFirst();
    }

    DrawGame(String label) {
        this.label = label;
    }
}
