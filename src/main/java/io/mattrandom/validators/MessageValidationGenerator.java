package io.mattrandom.validators;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
class MessageValidationGenerator {

    private final List<String> errorMessages = new ArrayList<>();
    private final List<String> errorCodes = new ArrayList<>();

    public void addErrorMessage(String errorMessage) {
        errorMessages.add(errorMessage);
    }

    public void addErrorCode(String errorCode) {
        errorCodes.add(errorCode);
    }
}
