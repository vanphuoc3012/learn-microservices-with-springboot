package com.microservices.gamification.game.badgeprocessor;

import com.microservices.gamification.challenge.ChallengeSolveDTO;
import com.microservices.gamification.game.domain.BadgeType;
import com.microservices.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SilverBadgeProcessor implements BadgeProcessor{
    @Override
    public Optional<BadgeType> processorForOptionalBadge(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolveDTO solved) {
        return currentScore >= 150 ?
                Optional.of(BadgeType.SILVER) :
                Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.SILVER;
    }
}
