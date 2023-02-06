package com.microservices.gamification.game.service;

import com.microservices.gamification.game.domain.LeaderBoardRow;
import com.microservices.gamification.game.repository.BadgeCardRepository;
import com.microservices.gamification.game.repository.ScoreCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LeaderBoardServiceImpl implements LeaderBoardService{
    @Autowired
    private ScoreCardRepository scoreCardRepository;
    @Autowired
    private BadgeCardRepository badgeCardRepository;

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        List<LeaderBoardRow> first10ScoreOnly = scoreCardRepository.findFirst10();
        return first10ScoreOnly.stream()
                .map(row -> {
                    List<String> badges =
                            badgeCardRepository.findBadgeCardsByUserIdOrderByBadgeTimeStamps(row.getUserId())
                                    .stream()
                                    .map(badgeCard -> badgeCard.getBadgeType().getDescription())
                                    .collect(Collectors.toList());
                    return row.withBadges(badges);
                }).collect(Collectors.toList());
    }
}
