package com.wtongze.lotteryapi;

import lombok.Data;

import java.util.List;

@Data
public class Game {
    public Integer number;
    public String name;
    public List<Draw> draws;
    public NextJackpot nextJackpot;
}
