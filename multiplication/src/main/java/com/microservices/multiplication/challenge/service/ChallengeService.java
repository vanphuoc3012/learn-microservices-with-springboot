package com.microservices.multiplication.challenge.service;

import com.microservices.multiplication.challenge.model.ChallengeAttempt;
import com.microservices.multiplication.challenge.dto.ChallengeAttemptDTO;

import java.util.List;

public interface ChallengeService {
    /**
     *
     * @param Verifies if an attempt coming from the presentation layer is correct or not
     * @return return resulting ChallengeAttempt Object
     */
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO);

    List<ChallengeAttempt> getStatsForUser(String userAlias);
}
