package com.wtongze.lotteryapi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Inquire {
    @Schema(example = "XF5NF&DYBHHPL")
    public String ticketSerialNumber;
}
