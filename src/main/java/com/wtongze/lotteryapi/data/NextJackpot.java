package com.wtongze.lotteryapi.data;

import lombok.Data;

import java.util.Date;

@Data
public class NextJackpot{
    public int drawNumber;
    public Date drawDate;
    public Date drawDateRaw;
    public Date drawDateLocal;
    public Date closeTime;
    public int jackpotAmount;
    public int estimatedCashValue;
}
