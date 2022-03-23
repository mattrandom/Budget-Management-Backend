package io.mattrandom.services;

import io.mattrandom.mappers.ExpenseMapper;
import io.mattrandom.repositories.ExpenseRepository;
import io.mattrandom.repositories.entities.ExpenseEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.ExpenseDto;
import io.mattrandom.validators.FilterSpecificRepositoryAbstract;
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
    private final FilterSpecificRepositoryAbstract filterSpecificRepository;

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
        Optional<ExpenseEntity> expenseEntity = expenseRepository.findById(expenseDto.getId());
        expenseEntity.ifPresent(entity -> checkAndUpdate(expenseDto, expenseEntity.get()));
        return expenseMapper.toDto(expenseEntity.get());

    }

    public List<ExpenseDto> getExpensesByFilteredConditions(Map<String, String> conditions) {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        List<ExpenseEntity> allFilteredExpenses = filterSpecificRepository.getAllFilteredData(loggedUserEntity, conditions);

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
