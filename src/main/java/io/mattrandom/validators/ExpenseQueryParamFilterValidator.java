package io.mattrandom.validators;

import io.mattrandom.enums.QueryParamMessageEnum;
import io.mattrandom.exceptions.ExpenseFilterQueryParamException;
import org.springframework.stereotype.Component;

@Component
public class ExpenseQueryParamFilterValidator extends QueryParamFilterValidator {

    @Override
    public void throwProperException(String missingQueryParamKey, String errorCode) {

        throw new ExpenseFilterQueryParamException(
                QueryParamMessageEnum.NO_EXPENSE_FILTER_PARAM_KEY.getMessage(missingQueryParamKey),
                errorCode
        );
    }
}
