package io.mattrandom.controllers.handlers;

import io.mattrandom.controllers.handlers.dtos.ErrorResponse;
import io.mattrandom.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error. Check 'errors' field for more details.", "1b78bf56-9882-4cfc-b113-0d20872a3097");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse propertyNotFoundException(PropertyIsNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getErrorCode());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse roomNotFoundException(RoomNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getErrorCode());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse expenseNotFoundException(ExpenseNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getErrorCode());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse rentNotFoundException(RentNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception exception, WebRequest request) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unknown error occurred", "6da8e862-e2a5-4725-9373-d816d0850130"));
    }
}
