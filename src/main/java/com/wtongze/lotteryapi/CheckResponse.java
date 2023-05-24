package com.wtongze.lotteryapi;

import lombok.Data;

import java.util.Date;

@Data
public class CheckResponse {
    public DrawGame gameName;
    public String rejectReason;
    public String serialNumber;
    public String status;
    public Date transactionTime;
}
