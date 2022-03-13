package io.mattrandom.enums;

import lombok.Getter;

@Getter
public enum AssetValidatorEnum {

    ASSETS_AMOUNT_NOT_SPECIFIED("'amount' field is not specified"),

    ASSETS_INCOME_DATE_NOT_SPECIFIED("'incomeDate' field is not specified");

    private final String reason;

    AssetValidatorEnum(String reason) {
        this.reason = reason;
    }
}
