package com.wtongze.lotteryapi;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Tag(name = "Game")
@RestController
public class GameController {
    private final WebClient client = WebClient.create();

    @GetMapping("/games")
    public Mono<DrawGamesResponse> getGames(@RequestParam Optional<Integer> count) {
        return client
                .get()
                .uri(url ->
                    url.scheme("https")
                            .host("calservice.calottery.com")
                            .path("/api/v1.5/drawgames")
                            .queryParam("drawscount", count.orElse(22))
                            .build()
                )
                .header("user-agent", "CA Lottery/3.9.0 (com.calottery.calottery; build:190; iOS 16.4.1) Alamofire/5.3.0")
                .retrieve().bodyToMono(DrawGamesResponse.class);
    }
}
