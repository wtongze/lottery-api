package com.wtongze.lotteryapi;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
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
                .uri(uri ->
                    uri.scheme("https")
                            .host("calservice.calottery.com")
                            .path("/api/v1.5/drawgames")
                            .queryParam("drawscount", count.orElse(22))
                            .build()
                )
                .headers(Config.setUserAgent)
                .retrieve()
                .bodyToMono(DrawGamesResponse.class);
    }

    @PostMapping("/check")
    public Mono<CheckResponse> checkTicket(@ModelAttribute Inquire inquire) {
        if (inquire.ticketSerialNumber == null || inquire.ticketSerialNumber.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return client
                .post()
                .uri(uri ->
                    uri.scheme("https")
                            .host("draw-mobile.calottery.com")
                            .path("/api/v2/draw-games/tickets/inquire")
                            .build()
                )
                .headers(Config.setAll)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inquire)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, e -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                })
                .bodyToMono(CheckResponse.class);
    }
}
