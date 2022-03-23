package io.mattrandom.validators;

import io.mattrandom.repositories.ExpenseRepository;
import io.mattrandom.repositories.entities.ExpenseEntity;
import io.mattrandom.repositories.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExpenseFilterSpecificRepository extends FilterSpecificRepositoryAbstract {

    private final ExpenseRepository expenseRepository;

    @Override
    protected List<ExpenseEntity> getExpensesByDateBetween(UserEntity user, LocalDateTime dateFom, LocalDateTime dateTo) {
        return expenseRepository.findByExpenseDateBetween(user, dateFom, dateTo);
    }
}
