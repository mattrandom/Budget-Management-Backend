package io.mattrandom.validators;

import io.mattrandom.enums.FilterExpensesConditionsEnum;
import io.mattrandom.enums.MonthSpecificationEnum;
import io.mattrandom.repositories.entities.ExpenseEntity;
import io.mattrandom.repositories.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public abstract class FilterSpecificRepositoryAbstract {

    @Autowired
    private ExpenseQueryParamFilterValidator expenseQueryParamFilterValidator;

    private static final String DATE_SUFFIX = "T00:00:00.00";

    public List<ExpenseEntity> getAllFilteredData(UserEntity user, Map<String, String> conditions) {
        expenseQueryParamFilterValidator.chooseFilter(conditions);

        if (isFromToQueryParamConditionsFound(conditions)) {
            String dateFrom = conditions.get(FilterExpensesConditionsEnum.DATE_FROM.getQueryParamKey());
            String dateTo = conditions.get(FilterExpensesConditionsEnum.DATE_TO.getQueryParamKey());
            return getExpensesByDateBetween(user, getLocalDateTimeParser(dateFrom), getLocalDateTimeParser(dateTo));

        } else if (isMonthYearQueryParamConditionsFound(conditions)) {
            MonthSpecificationEnum month = MonthSpecificationEnum.valueOf(conditions.get(FilterExpensesConditionsEnum.MONTH.getQueryParamKey()).toUpperCase());
            String year = conditions.get(FilterExpensesConditionsEnum.YEAR.getQueryParamKey());
            return getMonthlyExpensesByGivenYear(user, month, year);
        }

        return Collections.emptyList();
    }

    private boolean isFromToQueryParamConditionsFound(Map<String, String> conditions) {
        return conditions.containsKey(FilterExpensesConditionsEnum.DATE_FROM.getQueryParamKey())
                && conditions.containsKey(FilterExpensesConditionsEnum.DATE_TO.getQueryParamKey());
    }

    private boolean isMonthYearQueryParamConditionsFound(Map<String, String> conditions) {
        return conditions.containsKey(FilterExpensesConditionsEnum.YEAR.getQueryParamKey())
                && conditions.containsKey(FilterExpensesConditionsEnum.MONTH.getQueryParamKey());
    }

    private List<ExpenseEntity> getMonthlyExpensesByGivenYear(UserEntity user, MonthSpecificationEnum month, String year) {
        String dateFrom = month.getFirstDayOfGivenMonthAndYear(year);
        String dateTo = month.getLastDayOfGivenMonthAndYear(year);

        return getExpensesByDateBetween(user, getLocalDateTimeParser(dateFrom), getLocalDateTimeParser(dateTo));
    }

    private LocalDateTime getLocalDateTimeParser(String datePrefix) {
        return LocalDateTime.parse(datePrefix + DATE_SUFFIX);
    }

    protected abstract List<ExpenseEntity> getExpensesByDateBetween(UserEntity user, LocalDateTime dateFom, LocalDateTime dateTo);
}
