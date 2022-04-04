package io.mattrandom.exceptions;

import io.mattrandom.enums.OtherErrorCodes;
import lombok.Getter;

@Getter
public class PropertyIsNotFoundException extends RuntimeException {

    private final String errorCode;

    public PropertyIsNotFoundException(Long id) {
        super(String.format("Property with id=%d is not found", id));
        this.errorCode = OtherErrorCodes.PROPERTY_ERROR_CODE.getErrorCode();
    }
}
