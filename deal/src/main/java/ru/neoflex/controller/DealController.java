package ru.neoflex.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.neoflex.exceptions.ScoringException;
import ru.neoflex.dto.FinishRegistrationRequestDto;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;
import ru.neoflex.service.DealService;

import java.util.List;
import java.util.NoSuchElementException;

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
