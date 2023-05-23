package com.wtongze.lotteryapi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
