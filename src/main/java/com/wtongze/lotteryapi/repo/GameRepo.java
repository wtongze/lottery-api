package com.wtongze.lotteryapi.repo;

import com.wtongze.lotteryapi.data.CheckRequest;
import com.wtongze.lotteryapi.data.CheckResponse;
import com.wtongze.lotteryapi.data.DrawGamesResponse;
import com.wtongze.lotteryapi.data.HeaderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Repository
public class GameRepo {
    @Value("${game.endpoint}")
    private String endpoint;
    private final WebClient client = WebClient.create();

    private Mono<DrawGamesResponse> getGames(Integer gameId, Integer count) {
        return client
                .get()
                .uri(uri ->
                        uri.scheme("https")
                                .host(endpoint)
                                .path("/draw/api/v1.5/drawgames/" + (gameId == null ? "" : gameId))
                                .queryParam("drawscount", count == null ? 22 : count)
                                .build()
                )
                .headers(HeaderConfig.setUserAgent)
                .retrieve()
                .bodyToMono(DrawGamesResponse.class);
    }

    public Mono<DrawGamesResponse> getAllGames(Integer count) {
        return getGames(null, count);
    }

    public Mono<DrawGamesResponse> getGameById(Integer gameId, Integer count) {
        return getGames(gameId, count);
    }

    public Mono<CheckResponse> checkTicket(String serialNumber) {
        return client
                .post()
                .uri(uri ->
                        uri.scheme("https")
                                .host(endpoint)
                                .path("/check/api/v2/draw-games/tickets/inquire")
                                .build()
                )
                .headers(HeaderConfig.setAll)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CheckRequest(serialNumber))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, e -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                })
                .bodyToMono(CheckResponse.class);
    }
}
