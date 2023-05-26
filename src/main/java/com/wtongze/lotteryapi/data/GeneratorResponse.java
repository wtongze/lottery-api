package com.wtongze.lotteryapi.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class GeneratorResponse {
    public Date time = new Date();

    @Schema(example = "[[1, 2, 3]]")
    public List<List<Integer>> results = new ArrayList<>();
}
