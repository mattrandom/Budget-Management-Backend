package io.mattrandom.services.security.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PlainAuthenticationUserDto {

    private Long id;
    private String username;
}
