package io.mattrandom.validators;

import io.mattrandom.enums.ExpenseMessageEnum;
import io.mattrandom.exceptions.ExpenseFilterQueryParamException;
import org.springframework.stereotype.Component;

@Component
public class ExpenseQueryParamFilterValidator extends QueryParamFilterValidator {

    @Override
    public void throwProperException(String missingQueryParamKey, String errorCode) {

        throw new ExpenseFilterQueryParamException(
                ExpenseMessageEnum.NO_FILTER_PARAM_KEY.getMessage(missingQueryParamKey),
                errorCode
        );
    }
}
