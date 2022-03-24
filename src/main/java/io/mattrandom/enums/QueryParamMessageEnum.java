package io.mattrandom.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum QueryParamMessageEnum {

    NO_EXPENSE_FILTER_PARAM_KEY("Not specified query parameter for filtering Expenses: "),
    NO_ASSET_FILTER_PARAM_KEY("Not specified query parameter for filtering Assets: ");

    private final String message;

    public String getMessage(String missingQueryParamKey) {
        return this.message + missingQueryParamKey;
    }
}
