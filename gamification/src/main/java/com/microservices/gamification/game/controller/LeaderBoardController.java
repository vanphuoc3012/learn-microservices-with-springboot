package com.microservices.gamification.game.controller;

import com.microservices.gamification.game.domain.LeaderBoardRow;
import com.microservices.gamification.game.service.LeaderBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/leaders")
public class LeaderBoardController {

    private final LeaderBoardService leaderBoardService;

    @GetMapping
    public List<LeaderBoardRow> getLeaderBoard() {
        log.info("Retrieving leaderboard");
        List<LeaderBoardRow> currentLeaderBoard = leaderBoardService.getCurrentLeaderBoard();
        return currentLeaderBoard;
    }
}
