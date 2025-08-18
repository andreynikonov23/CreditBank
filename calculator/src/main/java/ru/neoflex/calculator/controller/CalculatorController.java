package ru.neoflex.calculator.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.calculator.dto.*;
import ru.neoflex.calculator.exceptions.ScoringException;
import ru.neoflex.calculator.service.CreditCalculator;

import java.util.List;

@RestController
@RequestMapping("/calculator")
@Slf4j
public class CalculatorController {
    private final CreditCalculator creditCalculator;

    @Autowired
    public CalculatorController(CreditCalculator creditCalculatorService) {
        this.creditCalculator = creditCalculatorService;
    }

    @PostMapping("/offers")
    public List<LoanOfferDto> offers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info("/calculator/offers with body: " + loanStatementRequestDto);
        return creditCalculator.calculateLoanTerms(loanStatementRequestDto);
    }

    @PostMapping("/calc")
    public CreditDto calc(@Valid @RequestBody ScoringDataDto scoringDataDto) throws ScoringException {
        log.info("/calculator/calc with body: " + scoringDataDto);
        return creditCalculator.calc(scoringDataDto);
    }

    @ExceptionHandler
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
        log.debug("valid error: " + errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleScoringExceptions(ScoringException ex) {
        String errorMessage = "the request failed scoring: " + ex.getMessage();
        log.debug(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
