package com.wtongze.lotteryapi;

import lombok.Data;

import java.util.List;

@Data
public class DrawGamesResponse {
    public List<Game> games;
}
