package io.mattrandom.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum QueryParamMessageEnum {

    NO_FILTER_PARAM_KEY("Not specified query parameter for filtering expenses: ");

    private final String message;

    public String getMessage(String missingQueryParamKey) {
        return this.message + missingQueryParamKey;
    }
}
