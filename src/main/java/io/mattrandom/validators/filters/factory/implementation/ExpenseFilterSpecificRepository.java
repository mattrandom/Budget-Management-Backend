package io.mattrandom.validators.filters.factory.implementation;

import io.mattrandom.repositories.ExpenseRepository;
import io.mattrandom.repositories.entities.ExpenseEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.validators.filters.factory.abstraction.AbstractFilterSpecificRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component("expenseFilterSpecificRepositoryBean")
@RequiredArgsConstructor
public class ExpenseFilterSpecificRepository extends AbstractFilterSpecificRepository<ExpenseEntity> {

    private final ExpenseRepository expenseRepository;

    @Override
    protected List<ExpenseEntity> getResultsFromProperRepositoryByDateBetween(UserEntity user, LocalDateTime dateFom, LocalDateTime dateTo, String category) {
        return expenseRepository.findByExpenseDateBetween(user, dateFom, dateTo);
    }
}
