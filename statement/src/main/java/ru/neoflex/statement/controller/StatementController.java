package ru.neoflex.statement.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public List<LoanOfferDto> calcLoanOffers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info("/statement with body {}", loanStatementRequestDto);
        return statementService.calcLoanOffers(loanStatementRequestDto);
    }

    @PostMapping("/offer")
    public void selectLoanOffer(@RequestParam(value = "denied-statement", required = false)String statementId, @Valid @RequestBody(required = false) LoanOfferDto loanOfferDto) {
        log.info("/statement/offer with param {} and body {}", statementId, loanOfferDto);
        if (statementId != null) {
            statementService.clientDenied(statementId);
        } else if (loanOfferDto == null) {
            String errorMessage = "loan offer is null";
            log.error("/deal/select/ error: {}", errorMessage);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        } else
            statementService.selectLoanOffer(loanOfferDto);
    }
}
