package io.mattrandom.exceptions;

import lombok.Getter;

@Getter
public class AssetFilterQueryParamException extends RuntimeException {

    private final String errorCode;

    public AssetFilterQueryParamException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
