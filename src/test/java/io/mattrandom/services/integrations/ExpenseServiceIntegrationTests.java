package io.mattrandom.services.integrations;

import io.mattrandom.enums.ExpenseCategory;
import io.mattrandom.enums.ExpenseMessageEnum;
import io.mattrandom.enums.FilterExpensesConditionsEnum;
import io.mattrandom.enums.MonthSpecificationEnum;
import io.mattrandom.exceptions.ExpenseFilterQueryParamException;
import io.mattrandom.repositories.entities.ExpenseEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.ExpenseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpenseServiceIntegrationTests extends AbstractIntegrationTestSchema {

    @Test
    void givenExpenseObject_whenAddExpense_thenShouldSaveObjectIntoDB() {
        //given
        UserEntity savedUserEntity = saveMockedUserInDB();

        ExpenseDto expenseDto = ExpenseDto.builder()
                .amount(BigDecimal.TEN)
                .expenseDate(LocalDateTime.now())
                .expenseCategory(ExpenseCategory.ENTERTAINMENT)
                .build();

        //when
        expenseService.addExpense(expenseDto);

        //then
        List<ExpenseEntity> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(1);
        assertThat(expenseList.get(0).getExpenseCategory()).isEqualTo(ExpenseCategory.ENTERTAINMENT);
    }

    @Test
    void givenExpenseObject_whenDeleteExpense_thenShouldDeleteObjectFromDB() {
        //given
        UserEntity savedUserEntity = saveMockedUserInDB();

        Long savedExpenseEntity = initializingExpenseDB(savedUserEntity);

        ExpenseDto expenseDto = ExpenseDto.builder()
                .id(savedExpenseEntity)
                .amount(BigDecimal.TEN)
                .expenseDate(LocalDateTime.now())
                .expenseCategory(ExpenseCategory.ENTERTAINMENT)
                .build();

        List<ExpenseEntity> fetchedExpenseEntityListBeforeDeleting = expenseRepository.findAll();
        assertThat(fetchedExpenseEntityListBeforeDeleting).hasSize(1);

        //when
        expenseService.deleteExpense(expenseDto);

        //then
        List<ExpenseEntity> fetchedExpenseEntityListAfterDeleting = expenseRepository.findAll();
        assertThat(fetchedExpenseEntityListAfterDeleting).hasSize(0);
        assertThat(fetchedExpenseEntityListAfterDeleting.size()).isEqualTo(0);
    }

    @Test
    void givenExpenseObject_whenUpdateExpense_thenShouldUpdateObjectInDB() {
        //given
        UserEntity savedUserEntity = saveMockedUserInDB();
        Long savedExpenseEntity = initializingExpenseDB(savedUserEntity);

        ExpenseDto expenseDto = ExpenseDto.builder()
                .id(savedExpenseEntity)
                .amount(BigDecimal.ONE)
                .expenseDate(LocalDateTime.now())
                .expenseCategory(ExpenseCategory.PERSONAL_DEVELOPMENT)
                .build();

        ExpenseEntity fetchedExpenseEntityById = expenseRepository.findById(savedExpenseEntity).get();
        assertThat(fetchedExpenseEntityById.getAmount()).isEqualTo(BigDecimal.TEN);
        assertThat(fetchedExpenseEntityById.getExpenseCategory()).isEqualTo(ExpenseCategory.ENTERTAINMENT);

        //when
        expenseService.updateExpense(expenseDto);

        //then
        ExpenseEntity expenseEntityAfterUpdating = expenseRepository.findById(savedExpenseEntity).get();
        assertThat(expenseEntityAfterUpdating.getAmount()).isEqualTo(BigDecimal.ONE);
        assertThat(expenseEntityAfterUpdating.getExpenseCategory()).isEqualTo(ExpenseCategory.PERSONAL_DEVELOPMENT);
    }

    @Test
    void givenExpenseObjects_whenGetAllExpenses_thenShouldReturnObjects() {
        //given
        UserEntity firstSavedUserEntity = saveMockedUserInDB();
        initializingExpenseDB(firstSavedUserEntity);
        initializingExpenseDB(firstSavedUserEntity);

        UserEntity secondSavedUserEntity = saveSecondMockedUserInDB();
        initializingExpenseDB(secondSavedUserEntity);

        //when
        List<ExpenseDto> allExpenses = expenseService.getAllExpenses();

        //then
        assertThat(allExpenses).hasSize(2);
    }

    @Test
    void givenExpenseObjects_whenGetExpensesByDateBetweenFromToConditions_thenShouldReturnObjects() {
        //given
        UserEntity user = saveMockedUserInDB();
        initializingExpenseDB(user, "2022-01-05");
        initializingExpenseDB(user, "2022-02-05");
        initializingExpenseDB(user, "2022-03-05");
        initializingExpenseDB(user, "2022-04-05");
        initializingExpenseDB(user, "2022-05-05");
        initializingExpenseDB(user, "2022-06-05");

        //when
        Map<String, String> fromToConditions = Map.of(
                FilterExpensesConditionsEnum.DATE_FROM.getQueryParamKey(), "2022-02-05",
                FilterExpensesConditionsEnum.DATE_TO.getQueryParamKey(), "2022-05-05"
        );
        List<ExpenseDto> allExpenses = expenseService.getExpensesByFilteredConditions(fromToConditions);

        //then
        assertThat(allExpenses).hasSize(4);
    }

    @Test
    void givenExpenseObjects_whenGetMonthlyExpensesByGivenYear_thenShouldReturnObjects() {
        //given
        UserEntity user = saveMockedUserInDB();
        initializingExpenseDB(user, "2022-01-05");
        initializingExpenseDB(user, "2022-04-01");
        initializingExpenseDB(user, "2022-04-05");
        initializingExpenseDB(user, "2022-04-15");
        initializingExpenseDB(user, "2022-04-30");
        initializingExpenseDB(user, "2022-06-05");

        //when
        Map<String, String> yearMonthConditions = Map.of(
                FilterExpensesConditionsEnum.MONTH.getQueryParamKey(), MonthSpecificationEnum.APRIL.name(),
                FilterExpensesConditionsEnum.YEAR.getQueryParamKey(), "2022"
        );
        List<ExpenseDto> allExpenses = expenseService.getExpensesByFilteredConditions(yearMonthConditions);

        //then
        assertThat(allExpenses).hasSize(4);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("notSpecifiedOneOfFilterParams")
    void given_when_then(String name, ParameterData data) {
        //given
        //when
        ExpenseFilterQueryParamException result = assertThrows(ExpenseFilterQueryParamException.class, () -> expenseService.getExpensesByFilteredConditions(data.getConditions()));

        //then
        assertThat(result.getMessage()).isEqualTo(ExpenseMessageEnum.NO_FILTER_PARAM_KEY.getMessage() + data.getNotSpecifiedQueryParam().getQueryParamKey());

    }

    private static Stream<Arguments> notSpecifiedOneOfFilterParams() {
        return Stream.of(
                Arguments.of("Test case for not specified query param: " + FilterExpensesConditionsEnum.DATE_TO.getQueryParamKey(),
                        new ParameterData(
                                Map.of(FilterExpensesConditionsEnum.DATE_FROM.getQueryParamKey(), "2022-02-05"),
                                FilterExpensesConditionsEnum.DATE_TO)
                ),

                Arguments.of("Test case for not specified query param: " + FilterExpensesConditionsEnum.DATE_FROM.getQueryParamKey(),
                        new ParameterData(
                                Map.of(FilterExpensesConditionsEnum.DATE_TO.getQueryParamKey(), "2022-02-05"),
                                FilterExpensesConditionsEnum.DATE_FROM)
                ),

                Arguments.of("Test case for not specified query param: " + FilterExpensesConditionsEnum.MONTH.getQueryParamKey(),
                        new ParameterData(
                                Map.of(FilterExpensesConditionsEnum.YEAR.getQueryParamKey(), "2022"),
                                FilterExpensesConditionsEnum.MONTH)
                ),

                Arguments.of("Test case for not specified query param: " + FilterExpensesConditionsEnum.YEAR.getQueryParamKey(),
                        new ParameterData(
                                Map.of(FilterExpensesConditionsEnum.MONTH.getQueryParamKey(), "april"),
                                FilterExpensesConditionsEnum.YEAR)
                )
        );
    }

    @Getter
    @AllArgsConstructor
    private static class ParameterData {
        private Map<String, String> conditions;
        private FilterExpensesConditionsEnum notSpecifiedQueryParam;
    }
}
