package io.mattrandom.controllers.handlers.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    private final int status;
    private final String errorMessage;
    private final String errorCode;

    private List<ValidationError> errors;

    public ErrorResponse(int status, String errorMessage, String errorCode) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    private static class ValidationError {
        private final String field;
        private final Object rejectedFieldContent;
        private final String message;
    }

    public void addValidationError(String field, Object rejectedFieldContent, String message) {
        if (Objects.isNull(this.errors)) {
            errors = new ArrayList<>();
        }
        errors.add(new ValidationError(field, rejectedFieldContent, message));
    }
}
