package io.mattrandom.constants;

import lombok.Getter;

@Getter
public enum SecurityConstants {
    JWT_SECRET("secretJWTService");

    private final String constant;

    SecurityConstants(String constant) {
        this.constant = constant;
    }
}
