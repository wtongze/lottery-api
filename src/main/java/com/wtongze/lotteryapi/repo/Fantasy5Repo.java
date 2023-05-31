package com.wtongze.lotteryapi.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Fantasy5Repo extends ReactiveCrudRepository<Fantasy5, Long> {
}
