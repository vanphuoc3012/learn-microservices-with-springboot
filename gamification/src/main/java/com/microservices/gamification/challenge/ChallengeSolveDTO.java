package com.microservices.gamification.challenge;

import lombok.Value;

@Value
public class ChallengeSolveDTO {
    long attemptId;
    boolean correct;
    int factorA;
    int factorB;
    long userId;
    String userAlias;
}
