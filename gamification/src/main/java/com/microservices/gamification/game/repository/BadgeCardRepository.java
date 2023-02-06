package com.microservices.gamification.game.repository;

import com.microservices.gamification.game.domain.BadgeCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeCardRepository extends JpaRepository<BadgeCard, Long> {

    /**
     * Retrieve all BadgeCards for a given user
     * @param userId the id of the user to look for BadgeCards
     * @return list of BadgeCards, ordered by most recent first
     */
    List<BadgeCard> findBadgeCardsByUserIdOrderByBadgeTimeStamps(Long userId);
}
