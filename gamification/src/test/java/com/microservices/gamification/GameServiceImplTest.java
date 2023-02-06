package com.microservices.gamification;

import com.microservices.gamification.game.badgeprocessor.BadgeProcessor;
import com.microservices.gamification.game.repository.BadgeCardRepository;
import com.microservices.gamification.game.repository.ScoreCardRepository;
import com.microservices.gamification.game.service.GameService;
import com.microservices.gamification.game.service.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {

    private GameService gameService;
    @Mock
    private ScoreCardRepository scoreCardRepository;
    @Mock
    private BadgeCardRepository badgeCardRepository;
    @Mock
    private List<BadgeProcessor> badgeProcessorList;

    @BeforeEach
    void setup() {
        gameService =
                new GameServiceImpl(scoreCardRepository,
                        badgeCardRepository,
                        badgeProcessorList);
    }

}
