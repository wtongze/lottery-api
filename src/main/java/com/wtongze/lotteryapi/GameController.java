package com.wtongze.lotteryapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Tag(name = "Game")
@SecurityRequirement(name = "JWT")
@RestController
public class GameController {
    private final WebClient client = WebClient.create();

    @GetMapping("/games")
    @Operation(
            summary = "Retrieve games info",
            description = "Get information of games including the latest draw results and jackpot information."
    )
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

    @PostMapping(
            value = "/check",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }
    )
    @Operation(
            summary = "Check a ticket",
            description = "Check winning status of a ticket by its serial number"
    )
    public Mono<CheckResponse> checkTicket(
            @ModelAttribute @Valid CheckRequest checkRequest,
            BindingResult result
    ) {
        if (result.hasErrors()) {
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
                .bodyValue(checkRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, e -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                })
                .bodyToMono(CheckResponse.class);
    }
}
