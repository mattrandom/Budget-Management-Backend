package io.mattrandom.controllers;

import io.mattrandom.services.ExpenseService;
import io.mattrandom.services.dtos.ExpenseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getAllExpenses() {
        return ResponseEntity.status(HttpStatus.OK).body(expenseService.getAllExpenses());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ExpenseDto>> getExpensesByFilteredConditions(@RequestParam Map<String, String> conditions) {
        return ResponseEntity.status(HttpStatus.OK).body(expenseService.getExpensesByFilteredConditions(conditions));
    }

    @PostMapping
    public ResponseEntity<ExpenseDto> addExpense(@RequestBody @Valid ExpenseDto expenseDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.addExpense(expenseDto));
    }

    @PutMapping
    public ResponseEntity<ExpenseDto> updateExpense(@RequestBody @Valid ExpenseDto expenseDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.updateExpense(expenseDto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteExpense(@RequestBody @Valid ExpenseDto expenseDto) {
        expenseService.deleteExpense(expenseDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
