package io.mattrandom.services.security.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthenticationUserDto {

    @Size(min = 3)
    private String username;

    @Size(min = 3)
    private String password;
}
