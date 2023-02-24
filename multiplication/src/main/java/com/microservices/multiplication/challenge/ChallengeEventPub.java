package com.microservices.multiplication.challenge;

import com.microservices.multiplication.challenge.dto.ChallengeSolvedEvent;
import com.microservices.multiplication.challenge.model.ChallengeAttempt;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChallengeEventPub {
    private final AmqpTemplate amqpTemplate;

    private final String challengesTopicExchange;

    public ChallengeEventPub(AmqpTemplate amqpTemplate,@Value("${amqp.exchange.attempts}") String challengesTopicExchange) {
        this.amqpTemplate = amqpTemplate;
        this.challengesTopicExchange = challengesTopicExchange;
    }

    public void challengeSolved(final ChallengeAttempt attempt) {
        ChallengeSolvedEvent solvedEvent = buildEvent(attempt);

        String routingKey = "attempt." + (solvedEvent.isCorrect() ? "correct" : "wrong");
        amqpTemplate.convertAndSend(challengesTopicExchange, routingKey, solvedEvent);
    }

    private ChallengeSolvedEvent buildEvent(final ChallengeAttempt attempt) {
        return new ChallengeSolvedEvent(attempt.getId(),
                attempt.isCorrect(),
                attempt.getFactorA(),
                attempt.getFactorB(),
                attempt.getUser().getId(),
                attempt.getUser().getAlias());
    }
}
