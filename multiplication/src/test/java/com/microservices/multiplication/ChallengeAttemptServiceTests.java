package com.microservices.multiplication;

import com.microservices.multiplication.challenge.controller.ChallengeAttemptController;
import com.microservices.multiplication.challenge.dto.ChallengeAttemptDTO;
import com.microservices.multiplication.challenge.model.ChallengeAttempt;
import com.microservices.multiplication.challenge.service.ChallengeService;
import com.microservices.multiplication.user.User;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(ChallengeAttemptController.class)
public class ChallengeAttemptServiceTests {

    @MockBean
    private ChallengeService challengeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ChallengeAttemptDTO> jsonRequestAttempt;
    @Autowired
    private JacksonTester<ChallengeAttempt> jsonResponse;


    @Test
    void postValidResult() throws Exception {
        //given
        User user = new User(1L, "John");
        long attemptId = 1L;
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 70, "john", 3500);
        ChallengeAttempt expectedResult = new ChallengeAttempt(attemptId, user, 50, 70, 3500, true);
        BDDMockito.given(challengeService.verifyAttempt(attemptDTO))
                .willReturn(expectedResult);

        //when
        MockHttpServletResponse response = mockMvc.perform(post("/attempts").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestAttempt.write(attemptDTO).getJson()))
                .andReturn().getResponse();

        //then
        BDDAssertions.then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        BDDAssertions.then(response.getContentAsString()).isEqualTo(
                jsonResponse.write(expectedResult).getJson());
    }

    @Test
    void postInvalidResult() throws Exception {
        //given
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(2000, -70, "john", 1);

        //when
        MockHttpServletResponse response = mockMvc.perform(post("/attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestAttempt.write(attemptDTO).getJson()))
                .andReturn().getResponse();

        //then
        BDDAssertions.then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}
