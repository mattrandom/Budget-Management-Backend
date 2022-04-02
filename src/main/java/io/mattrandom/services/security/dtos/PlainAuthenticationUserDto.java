package io.mattrandom.services.security.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class PlainAuthenticationUserDto {

    private Long id;

    @NotNull
    private String username;
}
