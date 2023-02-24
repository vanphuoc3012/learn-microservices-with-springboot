package com.microservices.gamification.game.badgeprocessor;

import com.microservices.gamification.challenge.ChallengeSolvedEvent;
import com.microservices.gamification.game.domain.BadgeType;
import com.microservices.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BronzeBadgeProcessor implements BadgeProcessor{
    @Override
    public Optional<BadgeType> processorForOptionalBadge(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedEvent solved) {
        return currentScore > 50 ?
                Optional.of(BadgeType.BRONZE) :
                Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.BRONZE;
    }
}
