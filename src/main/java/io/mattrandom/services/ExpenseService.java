package io.mattrandom.services;

import io.mattrandom.enums.FilterSpecificationEnum;
import io.mattrandom.exceptions.ExpenseNotFoundException;
import io.mattrandom.mappers.ExpenseMapper;
import io.mattrandom.repositories.ExpenseRepository;
import io.mattrandom.repositories.entities.ExpenseEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.ExpenseDto;
import io.mattrandom.validators.filters.strategy.RepositoryFilterStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final UserLoginService userLoginService;
    private final RepositoryFilterStrategy<ExpenseEntity> filterSpecificRepository;

    public List<ExpenseDto> getAllExpenses() {
        UserEntity user = userLoginService.getLoggedUserEntity();
        List<ExpenseEntity> expenseEntities = expenseRepository.findByUserEntity(user);
        return expenseMapper.toDtos(expenseEntities);
    }

    public ExpenseDto addExpense(ExpenseDto expenseDto) {
        UserEntity user = userLoginService.getLoggedUserEntity();
        ExpenseEntity expenseEntity = expenseMapper.toEntity(expenseDto, user);
        ExpenseEntity savedEntity = expenseRepository.save(expenseEntity);
        return expenseMapper.toDto(savedEntity);
    }

    public void deleteExpense(ExpenseDto expenseDto) {
        UserEntity user = userLoginService.getLoggedUserEntity();
        ExpenseEntity expenseEntity = expenseMapper.toEntity(expenseDto, user);
        expenseRepository.delete(expenseEntity);
    }

    @Transactional
    public ExpenseDto updateExpense(ExpenseDto expenseDto) {
        ExpenseEntity expenseEntity = expenseRepository.findById(expenseDto.getId())
                .orElseThrow(() -> new ExpenseNotFoundException(expenseDto.getId()));

        checkAndUpdate(expenseDto, expenseEntity);

        return expenseMapper.toDto(expenseEntity);

    }

    public List<ExpenseDto> getExpensesByFilteredConditions(Map<String, String> conditions) {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        FilterSpecificationEnum filterSpecification = FilterSpecificationEnum.EXPENSE_APPLICABLE;
        List<ExpenseEntity> allFilteredExpenses = filterSpecificRepository.chooseFilterAccordingToSpecification(loggedUserEntity, conditions, filterSpecification);

        return expenseMapper.toDtos(allFilteredExpenses);
    }

    private void checkAndUpdate(ExpenseDto expenseDto, ExpenseEntity expenseEntity) {
        if (Objects.nonNull(expenseDto.getAmount()) && !expenseDto.getAmount().equals(expenseEntity.getAmount())) {
            expenseEntity.setAmount(expenseDto.getAmount());
        }
        if (Objects.nonNull(expenseDto.getExpenseDate()) && !expenseDto.getExpenseDate().equals(expenseEntity.getExpenseDate())) {
            expenseEntity.setExpenseDate(expenseDto.getExpenseDate());
        }
        if (Objects.nonNull(expenseDto.getExpenseCategory()) && !expenseDto.getExpenseCategory().equals(expenseEntity.getExpenseCategory())) {
            expenseEntity.setExpenseCategory(expenseDto.getExpenseCategory());
        }
    }
}
