package io.mattrandom.enums;

import lombok.Getter;

@Getter
public enum AssetValidatorEnum {

    ASSETS_AMOUNT_NOT_SPECIFIED("'Amount' field is not specified");

    private final String reason;

    AssetValidatorEnum(String reason) {
        this.reason = reason;
    }
}
