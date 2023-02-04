package com.microservices.multiplication.user;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    private Long id;
    private String alias;
}
