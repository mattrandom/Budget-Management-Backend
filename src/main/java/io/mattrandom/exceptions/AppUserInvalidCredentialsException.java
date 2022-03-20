package io.mattrandom.exceptions;

import io.mattrandom.enums.AuthenticationEnum;

public class AppUserInvalidCredentialsException extends RuntimeException {

    public AppUserInvalidCredentialsException() {
        super(AuthenticationEnum.USER_INVALID_CREDENTIALS.getMessage());
    }
}
