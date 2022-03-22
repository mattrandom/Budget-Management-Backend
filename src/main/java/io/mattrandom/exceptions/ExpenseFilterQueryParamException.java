package io.mattrandom.exceptions;

import lombok.Getter;

@Getter
public class ExpenseFilterQueryParamException extends RuntimeException {

    private final String errorCode;

    public ExpenseFilterQueryParamException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
