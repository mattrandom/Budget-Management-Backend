package io.mattrandom.exceptions;

import io.mattrandom.enums.OtherErrorCodes;
import lombok.Getter;

@Getter
public class RoomNotFoundException extends RuntimeException {

    private final String errorCode;

    public RoomNotFoundException(Long id) {
        super(String.format("Room with id=%d is not found", id));
        this.errorCode = OtherErrorCodes.ROOM_ERROR_CODE.getErrorCode();
    }
}
