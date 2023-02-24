package com.microservices.gamification.game.service;

import com.microservices.gamification.challenge.ChallengeSolvedEvent;
import com.microservices.gamification.game.badgeprocessor.BadgeProcessor;
import com.microservices.gamification.game.domain.BadgeCard;
import com.microservices.gamification.game.domain.BadgeType;
import com.microservices.gamification.game.domain.ScoreCard;
import com.microservices.gamification.game.repository.BadgeCardRepository;
import com.microservices.gamification.game.repository.ScoreCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{
    private final ScoreCardRepository scoreCardRepository;
    private final BadgeCardRepository badgeCardRepository;
    private final List<BadgeProcessor> badgeProcessorList;

    @Override
    public GameResult newAttemptForUser(ChallengeSolvedEvent challenge) {
        if(challenge.isCorrect()) {
            ScoreCard scoreCard = new ScoreCard(challenge.getUserId(), challenge.getAttemptId());
            scoreCardRepository.save(scoreCard);
            log.info("User {} scored {} points for attempt id {}", challenge.getUserAlias(), scoreCard.getScore(), challenge.getAttemptId());
            List<BadgeCard> badgeCardList = processBadge(challenge);
            return new GameResult(scoreCard.getScore(), badgeCardList.stream().map(BadgeCard::getBadgeType).collect(Collectors.toList()));
        } else {
            log.info("Attempt id {} is not correct. User {} does not get score.",
                    challenge.getAttemptId(),
                    challenge.getUserAlias());
            return new GameResult(0, List.of());
        }
    }

    private List<BadgeCard> processBadge(
            final ChallengeSolvedEvent solvedChallenge) {
        long userId = solvedChallenge.getUserId();
        Optional<Integer> optUserTotalScore = scoreCardRepository.getTotalScoreForUser(userId);
        if(optUserTotalScore.isEmpty()) return Collections.emptyList();

        int totalScore = optUserTotalScore.get();

        List<ScoreCard> scoreCardList = scoreCardRepository.findByUserIdOrderByScoredTimestampDesc(userId);
        List<BadgeType> alreadyGotBadges =
                badgeCardRepository.findBadgeCardsByUserIdOrderByBadgeTimeStamps(userId)
                        .stream()
                        .map(BadgeCard::getBadgeType)
                        .collect(Collectors.toList());

        List<BadgeCard> newBadgeCards = badgeProcessorList.stream()
                .filter(bp -> !alreadyGotBadges.contains(bp.badgeType()))
                .map(bp -> bp.processorForOptionalBadge(totalScore, scoreCardList, solvedChallenge))
                .flatMap(Optional::stream)
                .map(badgeType -> new BadgeCard(userId, badgeType))
                .collect(Collectors.toList());

        badgeCardRepository.saveAll(newBadgeCards);
        return newBadgeCards;
    }
}
