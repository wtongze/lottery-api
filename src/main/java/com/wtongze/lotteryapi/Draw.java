package com.wtongze.lotteryapi;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Draw {
    public Integer drawNumber;
    public Date drawDate;
    public Date closeTime;
    public Integer jackpotAmount;
    public List<String> winningNumbers;
    public String raceTime;
    public List<Prize> prizes;
}
