package io.mattrandom.controllers.handlers;

import io.mattrandom.controllers.handlers.dtos.ErrorResponse;
import io.mattrandom.exceptions.AssetIncorrectException;
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
}
