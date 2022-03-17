package io.mattrandom.exceptions;

import io.mattrandom.enums.AuthenticationEnum;
import lombok.Getter;

@Getter
public class AppUserNotFoundException extends RuntimeException {

    public AppUserNotFoundException() {
        super(AuthenticationEnum.USER_NOT_FOUND.getMessage());
    }
}
