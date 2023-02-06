package com.microservices.gamification.game.controller;

import com.microservices.gamification.challenge.ChallengeSolveDTO;
import com.microservices.gamification.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attempts")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void postResult(@RequestBody ChallengeSolveDTO solveDTO) {
        gameService.newAttemptForUser(solveDTO);
    }
}
