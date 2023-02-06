package com.microservices.multiplication.challenge.dto;

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
