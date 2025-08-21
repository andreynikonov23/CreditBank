package ru.neoflex.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.neoflex.client.DealClientService;
import ru.neoflex.client.StatementClientService;
import ru.neoflex.dto.FinishRegistrationRequestDto;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;
import ru.neoflex.exceptions.ScoringException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/gateway")
@Slf4j
public class ApiController {
    private final StatementClientService statementClientService;
    private final DealClientService dealClientService;

    public ApiController(StatementClientService statementClientService, DealClientService dealClientService) {
        this.statementClientService = statementClientService;
        this.dealClientService = dealClientService;
    }

    @PostMapping("/statement")
    public List<LoanOfferDto> calcLoanOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info("/api/gateway/statement with body {}", loanStatementRequestDto);
        return statementClientService.calcLoanOffers(loanStatementRequestDto);
    }

    @PostMapping("/statement/offer")
    public void selectLoanOffer(@RequestBody LoanOfferDto loanOfferDto) {
        log.info("/api/gateway/statement/offer with body {}", loanOfferDto);
        statementClientService.offer(loanOfferDto);
    }

    @PostMapping("/deal/calculate")
    public void calculateCredit(@RequestParam("statementId") String statementId, @RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto) {
        log.info("/api/gateway/deal/calculate/{} with body {}", statementId, finishRegistrationRequestDto);
        dealClientService.calculate(statementId, finishRegistrationRequestDto);
    }

    @PostMapping("/deal/document/{statementId}/send")
    public void requestSendDocuments(@PathVariable("statementId") String statementId) {
        log.info("/api/gateway/deal/document/{}/send", statementId);
        dealClientService.requestSendDocuments(statementId);
    }

    @PostMapping("/deal/document/{statementId}/sign")
    public void requestSignDocuments(@PathVariable("statementId") String statementId) {
        log.info("/api/gateway/deal/document/{}/sign", statementId);
        dealClientService.requestSignDocuments(statementId);
    }

    @PostMapping("/deal/document/{statementId}/{code}")
    public void signDocument(@PathVariable("statementId") String statementId, @PathVariable("code") String sesCode) {
        log.info("/api/gateway/deal/document/{}/{}", statementId, sesCode);
        dealClientService.signDocument(statementId, sesCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
        log.debug("valid error: " + errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientExceptions(HttpClientErrorException ex) {
        log.debug("Service is not available: " + ex.getMessage());
        return new ResponseEntity<>("Unfortunately, the loan calculation service is currently unavailable",
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ScoringException.class)
    public ResponseEntity<String> handleScoringExceptions(ScoringException ex) {
        String errorMessage = "the request failed scoring: " + ex.getMessage();
        log.debug(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        String errorMessage = "no data was found in the database: " + ex.getMessage();
        log.debug(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
