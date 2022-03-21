package io.mattrandom.services.integrations;

import io.mattrandom.enums.ExpenseCategory;
import io.mattrandom.repositories.entities.ExpenseEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.ExpenseDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
}
