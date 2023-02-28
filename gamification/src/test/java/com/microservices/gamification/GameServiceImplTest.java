package com.microservices.gamification;

import com.microservices.gamification.challenge.ChallengeSolvedEvent;
import com.microservices.gamification.game.badgeprocessor.BadgeProcessor;
import com.microservices.gamification.game.domain.BadgeCard;
import com.microservices.gamification.game.domain.BadgeType;
import com.microservices.gamification.game.domain.ScoreCard;
import com.microservices.gamification.game.repository.BadgeCardRepository;
import com.microservices.gamification.game.repository.ScoreCardRepository;
import com.microservices.gamification.game.service.GameService;
import com.microservices.gamification.game.service.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {

    private GameService gameService;
    @Mock
    private ScoreCardRepository scoreCardRepository;
    @Mock
    private BadgeCardRepository badgeCardRepository;
    @Mock
    private BadgeProcessor badgeProcessor;

    @BeforeEach
    void setup() {
        gameService =
                new GameServiceImpl(scoreCardRepository,
                        badgeCardRepository,
                        List.of(badgeProcessor));
    }

    @Test
    public void processCorrectAttemptTest() {
        //given
        long userId = 1L;
        long attemptId = 10L;
        var attempt = new ChallengeSolvedEvent(attemptId, true, 20, 70, userId, "userAlias");
        var scoreCard = new ScoreCard(userId, attemptId);

            // given data from repository
        BDDMockito.given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(Optional.of(10));
        BDDMockito.given(scoreCardRepository.findByUserIdOrderByScoredTimestampDesc(userId))
                .willReturn(List.of(scoreCard));
        BDDMockito.given(badgeCardRepository.findBadgeCardsByUserIdOrderByBadgeTimeStamps(userId))
                .willReturn(List.of(new BadgeCard(userId, BadgeType.FIRST_WON)));

            // given data from service
        //when

        //then
    }
}
