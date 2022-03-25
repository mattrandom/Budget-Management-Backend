package io.mattrandom.validators.filters.factory.implementation;

import io.mattrandom.enums.QueryParamMessageEnum;
import io.mattrandom.exceptions.ExpenseFilterQueryParamException;
import io.mattrandom.validators.filters.factory.abstraction.AbstractQueryParamFilterValidator;
import org.springframework.stereotype.Component;

@Component("expenseQueryParamFilterValidatorBean")
class ExpenseQueryParamFilterValidator extends AbstractQueryParamFilterValidator {

    @Override
    public void throwProperException(String missingQueryParamKey, String errorCode) {

        throw new ExpenseFilterQueryParamException(
                QueryParamMessageEnum.NO_EXPENSE_FILTER_PARAM_KEY.getMessage(missingQueryParamKey),
                errorCode
        );
    }
}
