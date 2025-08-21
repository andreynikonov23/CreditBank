package ru.neoflex.statement.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.neoflex.statement.dto.LoanOfferDto;
import ru.neoflex.statement.dto.LoanStatementRequestDto;
import ru.neoflex.statement.service.StatementService;

import java.util.List;

@RestController
@RequestMapping("/statement")
@Slf4j
public class StatementController {
    private final StatementService statementService;

    public StatementController(StatementService statementServiceImpl) {
        this.statementService = statementServiceImpl;
    }

    @PostMapping("/")
    public List<LoanOfferDto> calcLoanOffers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info("/statement with body {}", loanStatementRequestDto);
        return statementService.calcLoanOffers(loanStatementRequestDto);
    }

    @PostMapping("/offer")
    public void selectLoanOffer(@Valid @RequestBody LoanOfferDto loanOfferDto) {
        log.info("/statement/offer with body {}", loanOfferDto);
        statementService.selectLoanOffer(loanOfferDto);
    }

    @ExceptionHandler
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
        log.debug("valid error: " + errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleHttpClientExceptions(HttpClientErrorException ex) {
        log.debug("MS calculator is not available: " + ex.getMessage());
        return new ResponseEntity<>("Unfortunately, the loan calculation service is currently unavailable",
                HttpStatus.SERVICE_UNAVAILABLE);
    }
}
