package com.wtongze.lotteryapi.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckRequest {
    @Schema(example = "XF5NF&DYBHHPL")
    @NotNull
    public String ticketSerialNumber;
}
