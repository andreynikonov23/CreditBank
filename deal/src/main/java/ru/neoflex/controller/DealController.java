package ru.neoflex.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.dto.FinishRegistrationRequestDto;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;
import ru.neoflex.service.DealService;

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
}
