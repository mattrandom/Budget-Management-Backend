package io.mattrandom.services.security.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthJwtDto {

    private final String jwt;
}
