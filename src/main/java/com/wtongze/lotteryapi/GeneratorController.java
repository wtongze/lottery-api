package com.wtongze.lotteryapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Tag(name = "Generator")
@RestController
public class GeneratorController {
    private final Generator generator = new Generator();

    @GetMapping("/generate")
    @Operation(description = "Generate Quick Picks for DrawGame")
    public GeneratorResponse loginTest(@RequestParam @Parameter(example = "powerball") String game,
                                       @RequestParam Optional<Integer> count) {
        DrawGame drawGame = DrawGame.fromString(game).orElse(null);
        if (drawGame == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        GeneratorResponse res = new GeneratorResponse();
        for (int i = 0; i < count.orElse(1); i++) {
            List<Integer> result = null;
            switch (drawGame) {
                case POWER_BALL -> {
                    List<Integer> whiteBalls = generator.intBetween(5, 69);
                    List<Integer> redBall = generator.intBetween(1, 26);
                    result = Stream.of(whiteBalls, redBall).flatMap(Collection::stream).toList();
                }
                case SUPER_LOTTO_PLUS -> {
                    List<Integer> whiteBalls = generator.intBetween(5, 47);
                    List<Integer> redBall = generator.intBetween(1, 27);
                    result = Stream.of(whiteBalls, redBall).flatMap(Collection::stream).toList();
                }
                case FANTASY_5 -> result = generator.intBetween(5, 39);
            }
            res.results.add(result);
        }

        return res;
    }
}
