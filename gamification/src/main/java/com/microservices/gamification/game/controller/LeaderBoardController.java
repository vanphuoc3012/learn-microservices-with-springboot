package com.microservices.gamification.game.controller;

import com.microservices.gamification.game.domain.LeaderBoardRow;
import com.microservices.gamification.game.service.LeaderBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaders")
public class LeaderBoardController {

    private final LeaderBoardService leaderBoardService;

    @GetMapping
    public List<LeaderBoardRow> getLeaderBoard() {
        List<LeaderBoardRow> currentLeaderBoard = leaderBoardService.getCurrentLeaderBoard();
        currentLeaderBoard.forEach(s ->
                System.out.println(s.getUserId())
        );
        return currentLeaderBoard;
    }
}
