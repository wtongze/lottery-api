package com.wtongze.lotteryapi.schedule;

import com.wtongze.lotteryapi.repo.Fantasy5;
import com.wtongze.lotteryapi.repo.Fantasy5Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduledTasks {
    private final Fantasy5Repo repo;

    @Autowired
    ScheduledTasks(Fantasy5Repo repo) {
        this.repo = repo;
    }

    @Scheduled(fixedDelay = 1000)
    public void fetchDraws() {
        var t = new Fantasy5();
        t.setDrawDate(LocalDateTime.now());
        t.setWinningNumbers(List.of("1", "2", "4"));
        repo.save(t).block();
    }
}
