package io.mattrandom.exceptions;

import io.mattrandom.enums.AuthenticationEnum;
import lombok.Getter;

@Getter
public class AppUserAlreadyExistException extends RuntimeException {

    public AppUserAlreadyExistException() {
        super(AuthenticationEnum.USER_ALREADY_EXIST.getMessage());
    }
}
