package com.microservices.multiplication.challenge.controller;

import com.microservices.multiplication.challenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/{idList}")
    public ResponseEntity<?> getUserByIdList(@PathVariable(name = "idList") final List<Long> userIdList) {
        log.info("Resolving user alias for user id: {}", userIdList);
        return ResponseEntity.ok(userRepository.findAllById(userIdList));
    }
}
