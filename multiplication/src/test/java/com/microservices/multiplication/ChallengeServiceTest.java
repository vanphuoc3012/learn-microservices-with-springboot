package com.microservices.multiplication;

import com.microservices.multiplication.challenge.dto.ChallengeAttemptDTO;
import com.microservices.multiplication.challenge.model.ChallengeAttempt;
import com.microservices.multiplication.challenge.service.ChallengeService;
import com.microservices.multiplication.challenge.service.ChallengeServiceImpl;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
public class ChallengeServiceTest {

    private ChallengeService challengeService;

    @BeforeEach
    public void setUp() {
        challengeService = new ChallengeServiceImpl();
    }

    @Test
    public void checkCorrectAttemptTest() {
        //given
        ChallengeAttemptDTO dto = new ChallengeAttemptDTO(11, 11, "user1", 121);

        //when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(dto);

        //then
        then(resultAttempt.isCorrect()).isTrue();
    }

    @Test
    public void checkWrongAttemptTest() {
        //given
        ChallengeAttemptDTO dto = new ChallengeAttemptDTO(11, 11, "user1", 122);

        //when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(dto);

        //then
        then(resultAttempt.isCorrect()).isFalse();
    }


}
