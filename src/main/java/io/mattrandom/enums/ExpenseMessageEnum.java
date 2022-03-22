package io.mattrandom.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExpenseMessageEnum {

    NO_FILTER_PARAM_KEY("Not specified query parameter for filtering expenses: ");

    private final String message;
}
