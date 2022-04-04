package io.mattrandom.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OtherErrorCodes {

    PROPERTY_ERROR_CODE("afa93fe1-5ed1-4eb2-9ce5-c7970c95ea13"),
    ROOM_ERROR_CODE("68e56520-f3a6-4dea-bcb3-9070205adadb"),
    EXPENSE_ERROR_CODE("e7adf08b-8e6e-4ab7-b8bf-f9eb61659306"),
    RENT_ERROR_CODE("98195b47-9d70-403a-8aa1-d679a8504167");

    private final String errorCode;
}
