package com.microservices.multiplication.challenge.repository;

import com.microservices.multiplication.challenge.model.ChallengeAttempt;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChallengeAttemptRepository extends CrudRepository<ChallengeAttempt, Long> {

    List<ChallengeAttempt> findTop10ByUserAliasOrderByIdDesc(String userAlias);
}
