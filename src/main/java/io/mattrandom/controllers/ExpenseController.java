package io.mattrandom.controllers;

import io.mattrandom.services.ExpenseService;
import io.mattrandom.services.dtos.ExpenseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(value = "REST API Expense CRUD Operations")
@RequiredArgsConstructor
@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    @ApiOperation("Fetch all Expenses from DB")
    public ResponseEntity<List<ExpenseDto>> getAllExpenses() {
        return ResponseEntity.status(HttpStatus.OK).body(expenseService.getAllExpenses());
    }

    @GetMapping("/filter")
    @ApiOperation("Fetch all Expenses filtered by query params from DB")
    public ResponseEntity<List<ExpenseDto>> getExpensesByFilteredConditions(@RequestParam Map<String, String> conditions) {
        return ResponseEntity.status(HttpStatus.OK).body(expenseService.getExpensesByFilteredConditions(conditions));
    }

    @PostMapping
    @ApiOperation("Save Expense")
    public ResponseEntity<ExpenseDto> addExpense(@RequestBody @Valid ExpenseDto expenseDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.addExpense(expenseDto));
    }

    @PutMapping
    @ApiOperation("Update Expense")
    public ResponseEntity<ExpenseDto> updateExpense(@RequestBody @Valid ExpenseDto expenseDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.updateExpense(expenseDto));
    }

    @DeleteMapping
    @ApiOperation("Delete Expense")
    public ResponseEntity<Void> deleteExpense(@RequestBody @Valid ExpenseDto expenseDto) {
        expenseService.deleteExpense(expenseDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
