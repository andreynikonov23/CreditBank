package ru.neoflex.statement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.statement.client.DealApiClient;
import ru.neoflex.statement.dto.LoanOfferDto;
import ru.neoflex.statement.dto.LoanStatementRequestDto;

import java.util.List;

@Service
@Slf4j
public class StatementServiceImpl implements StatementService {
    private final DealApiClient dealApiClient;

    public StatementServiceImpl(DealApiClient dealApiClientImpl) {
        this.dealApiClient = dealApiClientImpl;
    }
    @Override
    public List<LoanOfferDto> calcLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        return dealApiClient.statement(loanStatementRequestDto);
    }

    @Override
    public void selectLoanOffer(LoanOfferDto loanOfferDto) {
        dealApiClient.select(loanOfferDto);
    }

    @Override
    public void clientDenied(String statementId) {
        dealApiClient.clientDenied(statementId);
    }
}
