package com.microservices.gamification.game.service;

import com.microservices.gamification.game.domain.LeaderBoardRow;

import java.util.List;

public interface LeaderBoardService {
    /**
     *
     * @return current leader board rank high to low score
     */
    List<LeaderBoardRow> getCurrentLeaderBoard();
}
