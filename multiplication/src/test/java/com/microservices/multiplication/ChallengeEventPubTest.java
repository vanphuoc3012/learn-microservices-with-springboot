package com.microservices.multiplication;

import com.microservices.multiplication.challenge.ChallengeEventPub;
import com.microservices.multiplication.challenge.dto.ChallengeSolvedEvent;
import com.microservices.multiplication.challenge.model.ChallengeAttempt;
import com.microservices.multiplication.user.User;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

@ExtendWith(MockitoExtension.class)
public class ChallengeEventPubTest {
    private ChallengeEventPub challengeEventPub;
    @Mock
    private AmqpTemplate amqpTemplate;

    @BeforeEach
    public void setup() {
        challengeEventPub = new ChallengeEventPub(amqpTemplate, "test.topic");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void sendsAttempt(boolean correct) {
        //given
        ChallengeAttempt attempt = createTestAttempt(correct);

        //when
        challengeEventPub.challengeSolved(attempt);

        //then
        var exchangeCaptor = ArgumentCaptor.forClass(String.class);
        var routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        var eventCaptor = ArgumentCaptor.forClass(ChallengeSolvedEvent.class);

        Mockito.verify(amqpTemplate).convertAndSend(exchangeCaptor.capture(), routingKeyCaptor.capture(), eventCaptor.capture());

        BDDAssertions.then(exchangeCaptor.getValue()).isEqualTo("test.topic");
        BDDAssertions.then(routingKeyCaptor.getValue()).isEqualTo("attempt." + (correct ? "correct" : "wrong"));
        BDDAssertions.then(eventCaptor.getValue()).isEqualTo(solvedEvent(correct));
    }

    private ChallengeAttempt createTestAttempt(boolean correct) {
        return new ChallengeAttempt(1L, new User(10L, "john wick"), 30, 40, correct ? 1200 : 1300, correct);
    }

    private ChallengeSolvedEvent solvedEvent(boolean correct) {
        return new ChallengeSolvedEvent(1L, correct, 30, 40, 10L, "john wick");
    }
}
