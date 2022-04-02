package io.mattrandom.mappers;

import io.mattrandom.repositories.entities.ExpenseEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.ExpenseDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    ExpenseDto toDto(ExpenseEntity expenseEntity);

    List<ExpenseDto> toDtos(List<ExpenseEntity> entities);

    @Mapping(target = "userEntity", expression = "java(user)")
    ExpenseEntity toEntity(ExpenseDto expenseDto, @Context UserEntity user);
}
