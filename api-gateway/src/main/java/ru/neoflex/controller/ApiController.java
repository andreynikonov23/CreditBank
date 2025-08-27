package ru.neoflex.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.client.DealClientService;
import ru.neoflex.client.StatementClientService;
import ru.neoflex.dto.FinishRegistrationRequestDto;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;

import java.util.List;

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
    public List<LoanOfferDto> calcLoanOffers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
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
}
