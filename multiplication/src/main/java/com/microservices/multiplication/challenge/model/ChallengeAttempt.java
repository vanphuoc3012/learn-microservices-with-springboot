package com.microservices.multiplication.challenge.model;

import com.microservices.multiplication.user.User;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class ChallengeAttempt {
    private Long id;
    private User user;
    private int factorA;
    private int factorB;
    private int resultAttempt;
    private boolean correct;
}
