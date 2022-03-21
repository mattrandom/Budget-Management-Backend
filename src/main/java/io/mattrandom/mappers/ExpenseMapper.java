package io.mattrandom.mappers;

import io.mattrandom.repositories.entities.ExpenseEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.ExpenseDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    ExpenseDto toDto(ExpenseEntity expenseEntity);

    List<ExpenseDto> toDtos(List<ExpenseEntity> entities);

    default ExpenseEntity toEntity(ExpenseDto expenseDto, UserEntity user) {
        if (Objects.isNull(expenseDto)) {
            return null;
        }

        ExpenseEntity.ExpenseEntityBuilder builder = ExpenseEntity.builder();

        if (Objects.nonNull(expenseDto.getId())) {
            builder.id(expenseDto.getId());
        }

        if (Objects.nonNull(expenseDto.getAmount())) {
            builder.amount(expenseDto.getAmount());
        }

        if (Objects.nonNull(expenseDto.getExpenseDate())) {
            builder.expenseDate(expenseDto.getExpenseDate());
        }

        if (Objects.nonNull(expenseDto.getExpenseCategory())) {
            builder.expenseCategory(expenseDto.getExpenseCategory());
        }

        if (Objects.nonNull(user)) {
            builder.userEntity(user);
        }

        return builder.build();
    }
}
