package com.wtongze.lotteryapi.controller;

import com.wtongze.lotteryapi.data.CheckRequest;
import com.wtongze.lotteryapi.data.CheckResponse;
import com.wtongze.lotteryapi.data.DrawGame;
import com.wtongze.lotteryapi.data.DrawGamesResponse;
import com.wtongze.lotteryapi.repo.GameRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Tag(name = "Game")
@SecurityRequirement(name = "JWT")
@RestController
public class GameController {
    private final GameRepo gameRepo;

    @Autowired
    GameController(GameRepo gameRepo) {
        this.gameRepo = gameRepo;
    }

    @GetMapping("/games")
    @Operation(
            summary = "Retrieve games info",
            description = "Get information of games including the latest draw results and jackpot information."
    )
    public Mono<DrawGamesResponse> getGames(
            @RequestParam Optional<DrawGame> game,
            @RequestParam Optional<Integer> count
    ) {
        Integer resultCount = count.orElse(22);
        if (game.isPresent()) {
            return gameRepo.getGameById(game.get().gameId, resultCount);
        }
        return gameRepo.getAllGames(resultCount);
    }

    @PostMapping(
            value = "/check",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
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
        return gameRepo.checkTicket(checkRequest.ticketSerialNumber);
    }
}
