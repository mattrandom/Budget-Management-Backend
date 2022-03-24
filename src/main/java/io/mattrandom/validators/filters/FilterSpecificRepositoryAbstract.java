package io.mattrandom.validators.filters;

import io.mattrandom.enums.MonthSpecificationEnum;
import io.mattrandom.enums.QueryParamConditionsEnum;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.validators.AssetQueryParamFilterValidator;
import io.mattrandom.validators.ExpenseQueryParamFilterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public abstract class FilterSpecificRepositoryAbstract<T> {

    @Autowired
    private AssetQueryParamFilterValidator assetQueryParamFilterValidator;
    @Autowired
    private ExpenseQueryParamFilterValidator expenseQueryParamFilterValidator;

    private static final String DATE_SUFFIX = "T00:00:00.00";

    public List<T> getAllFilteredData(UserEntity user, Map<String, String> conditions) {

        switch (getFilterName()) {
            case "Asset" -> assetQueryParamFilterValidator.chooseFilter(conditions);
            case "Expense" -> expenseQueryParamFilterValidator.chooseFilter(conditions);
        }

        if (isFromToQueryParamConditionsFound(conditions)) {
            String dateFrom = conditions.get(QueryParamConditionsEnum.DATE_FROM.getQueryParamKey());
            String dateTo = conditions.get(QueryParamConditionsEnum.DATE_TO.getQueryParamKey());
            return getResultsFromProperRepositoryByDateBetween(user, getLocalDateTimeParser(dateFrom), getLocalDateTimeParser(dateTo));

        } else if (isMonthYearQueryParamConditionsFound(conditions)) {
            MonthSpecificationEnum month = MonthSpecificationEnum.valueOf(conditions.get(QueryParamConditionsEnum.MONTH.getQueryParamKey()).toUpperCase());
            String year = conditions.get(QueryParamConditionsEnum.YEAR.getQueryParamKey());
            return getMonthlyExpensesByGivenYear(user, month, year);
        }

        return Collections.emptyList();
    }

    private boolean isFromToQueryParamConditionsFound(Map<String, String> conditions) {
        return conditions.containsKey(QueryParamConditionsEnum.DATE_FROM.getQueryParamKey())
                && conditions.containsKey(QueryParamConditionsEnum.DATE_TO.getQueryParamKey());
    }

    private boolean isMonthYearQueryParamConditionsFound(Map<String, String> conditions) {
        return conditions.containsKey(QueryParamConditionsEnum.YEAR.getQueryParamKey())
                && conditions.containsKey(QueryParamConditionsEnum.MONTH.getQueryParamKey());
    }

    private List<T> getMonthlyExpensesByGivenYear(UserEntity user, MonthSpecificationEnum month, String year) {
        String dateFrom = month.getFirstDayOfGivenMonthAndYear(year);
        String dateTo = month.getLastDayOfGivenMonthAndYear(year);

        return getResultsFromProperRepositoryByDateBetween(user, getLocalDateTimeParser(dateFrom), getLocalDateTimeParser(dateTo));
    }

    private LocalDateTime getLocalDateTimeParser(String datePrefix) {
        return LocalDateTime.parse(datePrefix + DATE_SUFFIX);
    }

    protected abstract List<T> getResultsFromProperRepositoryByDateBetween(UserEntity user, LocalDateTime dateFom, LocalDateTime dateTo);

    protected abstract String getFilterName();
}