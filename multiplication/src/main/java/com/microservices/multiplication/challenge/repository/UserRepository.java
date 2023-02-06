package com.microservices.multiplication.challenge.repository;

import com.microservices.multiplication.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByAlias(String alias);
}
