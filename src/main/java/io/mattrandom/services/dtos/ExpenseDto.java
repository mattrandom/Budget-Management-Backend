package io.mattrandom.services.dtos;

import io.mattrandom.enums.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {

    private Long id;
    private BigDecimal amount;
    private LocalDateTime expenseDate;
    private ExpenseCategory expenseCategory;
}
