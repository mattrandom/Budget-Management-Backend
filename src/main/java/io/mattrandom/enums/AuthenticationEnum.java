package io.mattrandom.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthenticationEnum {

    USER_NOT_FOUND("User is not found"),
    USER_ALREADY_EXIST("User is already exist"),
    USER_INVALID_CREDENTIALS("Invalid credentials. Check username or password.");

    private final String message;
}
