package ru.neoflex.statement.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
    public void selectLoanOffer(@Valid @RequestBody LoanOfferDto loanOfferDto) {
        log.info("/statement/offer with body {}", loanOfferDto);
        statementService.selectLoanOffer(loanOfferDto);
    }
}
