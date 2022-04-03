package io.mattrandom.controllers;

import io.mattrandom.enums.MonthSpecificationEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calendar")
@Api(value = "Calendar API - GET")
public class CalendarController {

    @GetMapping
    @ApiOperation("Show month specification")
    public ResponseEntity<List<MonthSpecificationEnum>> getMonths() {
        return ResponseEntity.status(HttpStatus.OK).body(List.of(MonthSpecificationEnum.values()));
    }
}
