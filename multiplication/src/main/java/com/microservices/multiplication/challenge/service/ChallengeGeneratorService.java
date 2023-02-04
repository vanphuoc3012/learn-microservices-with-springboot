package com.microservices.multiplication.challenge.service;

import com.microservices.multiplication.challenge.model.Challenge;

public interface ChallengeGeneratorService {
    /**
     *
     * @return a randomly-generated challenge with factor between 11 and 99
     */
    Challenge randomChallenge();
}
