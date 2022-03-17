package io.mattrandom.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityEnumConstants {
    JWT_SECRET("secretJWTService"),
    JWT_PREFIX("Bearer ");

    private final String constant;
}
