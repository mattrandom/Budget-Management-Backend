package io.mattrandom.services.security.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUserDto {

    private String username;
    private String password;
}
