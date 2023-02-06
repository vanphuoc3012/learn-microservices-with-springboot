package com.microservices.gamification.game.repository;

import com.microservices.gamification.game.domain.LeaderBoardRow;
import com.microservices.gamification.game.domain.ScoreCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScoreCardRepository extends JpaRepository<ScoreCard, Long> {

    /**
     * Retriveves a list of {@link LeaderBoardRow}s representing the Leader Board
     * of users and their total score.
     * @return
     */
    @Query("SELECT new com.microservices.gamification.game.domain.LeaderBoardRow(sc.userId, SUM(sc.score)) " +
            "FROM ScoreCard sc GROUP BY sc.userId ORDER BY SUM(sc.score) DESC")
    List<LeaderBoardRow> findFirst10();

    /**
     * Gets the total score for a given user: the sum of the scores of all their ScoreCards
     * @param userId the id of user
     * @return the total score of user, empty if user doesn't exist
     */
    @Query("SELECT SUM(sc.score) FROM ScoreCard sc WHERE sc.userId = ?1 GROUP BY sc.userId")
    Optional<Integer> getTotalScoreForUser(Long userId);

    List<ScoreCard> findByUserIdOrderByScoredTimestampDesc(final Long userId);
}
