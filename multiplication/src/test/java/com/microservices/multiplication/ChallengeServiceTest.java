package com.microservices.multiplication;

import com.microservices.multiplication.challenge.ChallengeEventPub;
import com.microservices.multiplication.challenge.dto.ChallengeAttemptDTO;
import com.microservices.multiplication.challenge.model.ChallengeAttempt;
import com.microservices.multiplication.challenge.repository.ChallengeAttemptRepository;
import com.microservices.multiplication.challenge.repository.UserRepository;
import com.microservices.multiplication.challenge.service.ChallengeService;
import com.microservices.multiplication.challenge.service.ChallengeServiceImpl;
import com.microservices.multiplication.user.User;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ChallengeServiceTest {

    private ChallengeService challengeService;

    @Mock
    private ChallengeAttemptRepository attemptRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ChallengeEventPub challengeEventPub;

    @BeforeEach
    public void setUp() {
        challengeService = new ChallengeServiceImpl(attemptRepository, userRepository, challengeEventPub);

    }

    @Test
    public void checkCorrectAttemptTest() {
        //given
        BDDMockito.given(attemptRepository.save(any()))
                .will(AdditionalAnswers.returnsFirstArg());
        ChallengeAttemptDTO dto = new ChallengeAttemptDTO(11, 11, "user1", 121);

        //when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(dto);

        //then
        then(resultAttempt.isCorrect()).isTrue();

        Mockito.verify(userRepository).save(new User("user1"));
        Mockito.verify(attemptRepository).save(resultAttempt);
        Mockito.verify(challengeEventPub);
    }

    @Test
    public void checkWrongAttemptTest() {
        //given
        BDDMockito.given(attemptRepository.save(any()))
                .will(AdditionalAnswers.returnsFirstArg());
        ChallengeAttemptDTO dto = new ChallengeAttemptDTO(11, 11, "user1", 122);

        //when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(dto);

        //then
        then(resultAttempt.isCorrect()).isFalse();
        Mockito.verify(userRepository).save(new User("user1"));
        Mockito.verify(attemptRepository).save(resultAttempt);
    }

    @Test
    public void checkExistingUserTest() {
        //given
        BDDMockito.given(attemptRepository.save(any()))
                .will(AdditionalAnswers.returnsFirstArg());
        User exsistingUser = new User(1L, "user1");
        BDDMockito.given(userRepository.findByAlias("user1"))
                .willReturn(Optional.of(exsistingUser));
        ChallengeAttemptDTO attemptDTO =
                new ChallengeAttemptDTO(50, 60, "user1", 3000);

        //when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        //then
        BDDAssertions.then(resultAttempt.isCorrect()).isTrue();
        BDDAssertions.then(resultAttempt.getUser()).isEqualTo(exsistingUser);
        Mockito.verify(userRepository, Mockito.never()).save(any());
        Mockito.verify(attemptRepository).save(resultAttempt);
    }

    @Test
    public void getStatsForUserTest() {
        //given
        User user = new User("user1");
        ChallengeAttempt attempt1 = new ChallengeAttempt(1L, user,50, 60, 3000, true);
        ChallengeAttempt attempt2 = new ChallengeAttempt(2L, user,50, 70, 3600, false);
        List<ChallengeAttempt> attemptList = List.of(attempt1, attempt2);
        BDDMockito.given(attemptRepository.findTop10ByUserAliasOrderByIdDesc("user1"))
                .willReturn(attemptList);
        //when
        List<ChallengeAttempt> statsForUser = challengeService.getStatsForUser("user1");

        //then
        BDDAssertions.then(statsForUser).isEqualTo(attemptList);
    }

}
