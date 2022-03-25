package io.mattrandom.controllers.handlers;

import io.mattrandom.controllers.handlers.dtos.ErrorResponse;
import io.mattrandom.exceptions.AppUserInvalidCredentialsException;
import io.mattrandom.exceptions.AssetFilterQueryParamException;
import io.mattrandom.exceptions.AssetIncorrectException;
import io.mattrandom.exceptions.ExpenseFilterQueryParamException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse assetIncorrectExceptionHandler(AssetIncorrectException assetIncorrectException) {
        return new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), assetIncorrectException.getMessage(), assetIncorrectException.getErrorCode());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse appUserInvalidCredentialsExceptionHandler(AppUserInvalidCredentialsException ex) {
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage(), "ac0f60c0-591d-4d44-9604-45d3ccf5610c");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse assetFilterQueryParamExceptionHandler(AssetFilterQueryParamException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getErrorCode());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse expenseFilterQueryParamExceptionHandler(ExpenseFilterQueryParamException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getErrorCode());
    }
}
