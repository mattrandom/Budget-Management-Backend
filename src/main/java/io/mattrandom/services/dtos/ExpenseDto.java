package io.mattrandom.services.dtos;

import io.mattrandom.enums.ExpenseCategory;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ExpenseDto {

    private Long id;

    @NotNull
    private BigDecimal amount;

    private LocalDateTime expenseDate;

    @NotNull
    private ExpenseCategory expenseCategory;
}
