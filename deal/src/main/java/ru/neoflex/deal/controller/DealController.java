package ru.neoflex.deal.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.neoflex.deal.exceptions.ScoringException;
import ru.neoflex.deal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.deal.model.dto.LoanOfferDto;
import ru.neoflex.deal.model.dto.LoanStatementRequestDto;
import ru.neoflex.deal.service.DealService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/deal")
public class DealController {
    private final DealService dealService;

    public DealController(DealService dealServiceImpl) {
        this.dealService = dealServiceImpl;
    }

    @PostMapping("/statement")
    public List<LoanOfferDto> calcLoanTerms(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto){
        log.info("/deal/statement with body {}", loanStatementRequestDto);
        return dealService.calcLoanTerms(loanStatementRequestDto);
    }

    @PostMapping("/select")
    public void selectLoanOffer(@Valid @RequestBody LoanOfferDto loanOfferDto) {
        log.info("/deal/select/ with body {}", loanOfferDto);
        dealService.selectLoanOffer(loanOfferDto);
    }

    @PostMapping("/calculate")
    public void calculate(@RequestParam("statementId") String statementId, @Valid @RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto) {
        log.info("/deal/calculate/{} with body {}", statementId, finishRegistrationRequestDto);
        dealService.calculate(statementId, finishRegistrationRequestDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
        log.debug("valid error: " + errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientExceptions(HttpClientErrorException ex) {
        log.debug("MS calculator is not available: " + ex.getMessage());
        return new ResponseEntity<>("Unfortunately, the loan calculation service is currently unavailable",
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ScoringException.class)
    public ResponseEntity<String> handleScoringExceptions(ScoringException ex) {
        String errorMessage = "the request failed scoring: " + ex.getMessage();
        log.debug(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
