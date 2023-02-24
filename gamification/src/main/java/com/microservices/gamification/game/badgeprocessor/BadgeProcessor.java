package com.microservices.gamification.game.badgeprocessor;

import com.microservices.gamification.challenge.ChallengeSolvedEvent;
import com.microservices.gamification.game.domain.BadgeType;
import com.microservices.gamification.game.domain.ScoreCard;

import java.util.List;
import java.util.Optional;

public interface BadgeProcessor {
    /**
     * Processes some or all of the passed parameters and decides if the user is entitled to badge
     * @return a BadgeType if the user is entitled to this badge, otherwise return empty
     */
    Optional<BadgeType> processorForOptionalBadge(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedEvent solved);

    /**
     * @return the BadgeType object that this processor is handling. You can use it to filter processors according to your needs.
     */
    BadgeType badgeType();

}
