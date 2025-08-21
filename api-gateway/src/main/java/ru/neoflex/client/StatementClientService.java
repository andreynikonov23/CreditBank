package ru.neoflex.client;

import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;

import java.util.List;

public interface StatementClientService extends ApiClient{
    List<LoanOfferDto> calcLoanOffers(LoanStatementRequestDto loanStatementRequestDto);
    void offer(LoanOfferDto loanOfferDto);
}
