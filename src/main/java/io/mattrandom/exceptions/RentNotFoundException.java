package io.mattrandom.exceptions;

import io.mattrandom.enums.OtherErrorCodes;
import lombok.Getter;

@Getter
public class RentNotFoundException extends RuntimeException {

    private final String errorCode;

    public RentNotFoundException(Long id) {
        super(String.format("Rent with id=%d is not found", id));
        this.errorCode = OtherErrorCodes.RENT_ERROR_CODE.getErrorCode();
    }
}
