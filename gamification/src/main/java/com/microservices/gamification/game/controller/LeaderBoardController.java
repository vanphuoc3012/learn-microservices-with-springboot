package com.microservices.gamification.game.controller;

import com.microservices.gamification.game.service.LeaderBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaders")
public class LeaderBoardController {

    private final LeaderBoardService leaderBoardService;

    @GetMapping
    public ResponseEntity<?> getLeaderBoard() {
        return ResponseEntity.ok(leaderBoardService.getCurrentLeaderBoard());
    }
}
