package ru.neoflex.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.dto.CreditDto;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;
import ru.neoflex.dto.ScoringDataDto;
import ru.neoflex.exceptions.ScoringException;
import ru.neoflex.service.CreditCalculator;

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
}
