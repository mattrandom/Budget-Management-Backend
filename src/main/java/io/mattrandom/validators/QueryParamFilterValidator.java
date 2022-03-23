package io.mattrandom.validators;

import io.mattrandom.enums.FilterExpensesConditionsEnum;

import java.util.Map;

public abstract class QueryParamFilterValidator {

    public void chooseFilter(Map<String, String> conditions) {
        verifyLackOfToKeyQueryParamURL(conditions, "07b1969f-519f-494e-af7f-69f73eb92503");
        verifyLackOfFromKeyQueryParamURL(conditions, "41eead82-8d83-4999-a4f7-e4b6157a66c0");
        verifyLackOfMonthKeyQueryParamURL(conditions, "7d0a3cf5-0d6e-4a53-900d-8c4077edec19");
        verifyLackOfYearKeyQueryParamURL(conditions, "6601ed1c-0931-461c-a3cd-609e97d6b123");
    }

    private void verifyLackOfToKeyQueryParamURL(Map<String, String> conditions, String errorCode) {
        if (conditions.containsKey(FilterExpensesConditionsEnum.DATE_FROM.getQueryParamKey())
                && !conditions.containsKey(FilterExpensesConditionsEnum.DATE_TO.getQueryParamKey())) {
            throwProperException(FilterExpensesConditionsEnum.DATE_TO.getQueryParamKey(), errorCode);
        }
    }

    private void verifyLackOfFromKeyQueryParamURL(Map<String, String> conditions, String errorCode) {
        if (conditions.containsKey(FilterExpensesConditionsEnum.DATE_TO.getQueryParamKey())
                && !conditions.containsKey(FilterExpensesConditionsEnum.DATE_FROM.getQueryParamKey())) {
            throwProperException(FilterExpensesConditionsEnum.DATE_FROM.getQueryParamKey(), errorCode);
        }
    }

    private void verifyLackOfMonthKeyQueryParamURL(Map<String, String> conditions, String errorCode) {
        if (conditions.containsKey(FilterExpensesConditionsEnum.YEAR.getQueryParamKey())
                && !conditions.containsKey(FilterExpensesConditionsEnum.MONTH.getQueryParamKey())) {
            throwProperException(FilterExpensesConditionsEnum.MONTH.getQueryParamKey(), errorCode);
        }
    }

    private void verifyLackOfYearKeyQueryParamURL(Map<String, String> conditions, String errorCode) {
        if (conditions.containsKey(FilterExpensesConditionsEnum.MONTH.getQueryParamKey())
                && !conditions.containsKey(FilterExpensesConditionsEnum.YEAR.getQueryParamKey())) {
            throwProperException(FilterExpensesConditionsEnum.YEAR.getQueryParamKey(), errorCode);
        }
    }

    public abstract void throwProperException(String missingQueryParamKey, String errorCode);
}
