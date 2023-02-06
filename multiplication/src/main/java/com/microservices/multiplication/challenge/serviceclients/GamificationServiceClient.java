package com.microservices.multiplication.challenge.serviceclients;

import com.microservices.multiplication.challenge.dto.ChallengeSolveDTO;
import com.microservices.multiplication.challenge.model.ChallengeAttempt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GamificationServiceClient {

    private final RestTemplate restTemplate;

    private final String gamificationHostUrl;

    public GamificationServiceClient(final RestTemplateBuilder builder,
                                     @Value("${service.gamification.host}") final String gamificationHostUrl) {
        this.restTemplate = builder.build();
        this.gamificationHostUrl = gamificationHostUrl;
    }

    public boolean sendAttempt(final ChallengeAttempt attempt) {
        try {
            ChallengeSolveDTO solveDTO = new ChallengeSolveDTO(
                    attempt.getId(), attempt.isCorrect(), attempt.getFactorA(), attempt.getFactorB(), attempt.getUser().getId(), attempt.getUser().getAlias());

            String url = gamificationHostUrl + "attempts";
            ResponseEntity<String> response = restTemplate.postForEntity(url, solveDTO, String.class);
            log.info("Gamification service response: {}", response.getStatusCode());
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            log.error("There was a problem sending the attempt.", e);
            return false;
        }
    }
}
