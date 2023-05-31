package com.wtongze.lotteryapi.data;

public enum DrawGame {
    POWERBALL(12),
    SUPERLOTTO(8),
    FANTASY5(10),
    DAILY3(9),
    DAILY4(14);

    public final Integer gameId;
    DrawGame(Integer gameId) {
        this.gameId = gameId;
    }
}
