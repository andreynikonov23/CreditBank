package ru.neoflex.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Operation(summary = "Prescoring and calculation of possible loan offers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LoanOfferDto.class, type = "array")))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(mediaType = "application/json", examples = @ExampleObject("[invalid email, invalid passport series, invalid passport series, age must be over 18 years old., lastname is empty]")))
    })
    @PostMapping("/offers")
    public List<LoanOfferDto> offers(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details for the loan")
            @Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info("/calculator/offers with body: " + loanStatementRequestDto);
        return creditCalculator.calculateLoanTerms(loanStatementRequestDto);
    }

    @Operation(summary = "Scoring and credit calculation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreditDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(mediaType = "application/json", examples = @ExampleObject("[invalid email, invalid passport series, invalid passport series, age must be over 18 years old., lastname is empty]")))
    })
    @PostMapping("/calc")
    public CreditDto calc(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credit details")
            @Valid @RequestBody ScoringDataDto scoringDataDto) throws ScoringException {
        log.info("/calculator/calc with body: " + scoringDataDto);
        return creditCalculator.calc(scoringDataDto);
    }
}
