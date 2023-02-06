package com.microservices.gamification.game.service;

import com.microservices.gamification.challenge.ChallengeSolveDTO;
import com.microservices.gamification.game.domain.BadgeType;
import lombok.Value;

import java.util.List;

public interface GameService {

    GameResult newAttemptForUser(ChallengeSolveDTO challenge);

    @Value
    class GameResult {
        int score;
        List<BadgeType> badges;
    }
}
