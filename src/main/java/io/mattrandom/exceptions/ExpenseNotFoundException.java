package io.mattrandom.exceptions;

import io.mattrandom.enums.OtherErrorCodes;
import lombok.Getter;

@Getter
public class ExpenseNotFoundException extends RuntimeException {

    private final String errorCode;

    public ExpenseNotFoundException(Long id) {
        super(String.format("Expense with id=%d is not found", id));
        this.errorCode = OtherErrorCodes.EXPENSE_ERROR_CODE.getErrorCode();
    }
}
