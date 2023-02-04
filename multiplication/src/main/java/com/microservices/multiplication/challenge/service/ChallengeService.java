package com.microservices.multiplication.challenge.service;

import com.microservices.multiplication.challenge.model.ChallengeAttempt;
import com.microservices.multiplication.challenge.dto.ChallengeAttemptDTO;

public interface ChallengeService {
    /**
     *
     * @param Verifes if an attempt coming from the presentation layer is correct or not
     * @return return resulting ChallengeAttempt Object
     */
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO);
}
