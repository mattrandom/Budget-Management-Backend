package io.mattrandom.services.dtos;

import io.mattrandom.enums.ExpenseCategory;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ExpenseDto {

    private Long id;
    private BigDecimal amount;
    private LocalDateTime expenseDate;
    private ExpenseCategory expenseCategory;
}
