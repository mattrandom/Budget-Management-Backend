package io.mattrandom.constants;

import lombok.Getter;

@Getter
public enum SecurityConstants {
    JWT_SECRET("secretJWTService"),
    JWT_PREFIX("Bearer ");

    private final String constant;

    SecurityConstants(String constant) {
        this.constant = constant;
    }
}
