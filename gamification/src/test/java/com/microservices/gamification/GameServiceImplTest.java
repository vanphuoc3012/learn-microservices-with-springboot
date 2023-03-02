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
}
