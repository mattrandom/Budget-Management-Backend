package io.mattrandom.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FilterExpensesConditionsEnum {
    DATE_FROM("from"),
    DATE_TO("to"),
    YEAR("year"),
    MONTH("month");

    private final String queryParamKey;
}
