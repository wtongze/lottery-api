package com.wtongze.lotteryapi.repo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Table("fantasy_5")
public class Fantasy5 {
    @Id
    private Long id;

    private List<String> winningNumbers;

    private LocalDateTime drawDate;
}
