package io.mattrandom.exceptions;

import lombok.Getter;

@Getter
public class AssetIncorrectException extends RuntimeException {

    private final String errorCode;

    public AssetIncorrectException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
