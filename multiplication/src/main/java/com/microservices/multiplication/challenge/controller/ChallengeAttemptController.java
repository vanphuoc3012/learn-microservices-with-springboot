package com.microservices.multiplication.challenge.controller;

import com.microservices.multiplication.challenge.dto.ChallengeAttemptDTO;
import com.microservices.multiplication.challenge.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/attempts")
@Slf4j
@RequiredArgsConstructor
public class ChallengeAttemptController {
    private final ChallengeService challengeService;

    @PostMapping("")
    public ResponseEntity<?> postChallengeAttemptResult(
            @Valid @RequestBody ChallengeAttemptDTO challengeAttemptDTO) {
        log.info("Received new attempt from {}", challengeAttemptDTO.getUserAlias());
        return ResponseEntity.ok(challengeService.verifyAttempt(challengeAttemptDTO));
    }

    @GetMapping
    ResponseEntity<?> getStatistics(@RequestParam(name = "alias") String alias) {
        return ResponseEntity.ok(challengeService.getStatsForUser(alias));
    }
}
