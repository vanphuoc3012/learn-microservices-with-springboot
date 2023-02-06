package com.microservices.multiplication.challenge.service;

import com.microservices.multiplication.challenge.dto.ChallengeAttemptDTO;
import com.microservices.multiplication.challenge.model.ChallengeAttempt;
import com.microservices.multiplication.challenge.repository.ChallengeAttemptRepository;
import com.microservices.multiplication.challenge.repository.UserRepository;
import com.microservices.multiplication.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChallengeServiceImpl implements ChallengeService{

    private final ChallengeAttemptRepository attemptRepository;
    private final UserRepository userRepository;

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO) {
        //check if user already exists for that alias, otherwise create it
        User user = userRepository.findByAlias(attemptDTO.getUserAlias())
                .orElseGet(() -> {
                    log.info("Creating new user with alias: {}", attemptDTO.getUserAlias());
                    return userRepository.save(new User(attemptDTO.getUserAlias()));
                });
        //check if the attempt is correct
        boolean isCorrect = attemptDTO.getGuess() == attemptDTO.getFactorA() * attemptDTO.getFactorB();
        ChallengeAttempt checkedAttempt = new ChallengeAttempt(
            null, user, attemptDTO.getFactorA(), attemptDTO.getFactorB(), attemptDTO.getGuess(), isCorrect);
        //store the attempt

        return attemptRepository.save(checkedAttempt);
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.findTop10ByUserAliasOrderByIdDesc(userAlias);
    }
}
