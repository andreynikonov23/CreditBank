package ru.neoflex.statement.service;

import ru.neoflex.statement.dto.LoanOfferDto;
import ru.neoflex.statement.dto.LoanStatementRequestDto;

import java.util.List;

public interface StatementService {
    List<LoanOfferDto> calcLoanOffers(LoanStatementRequestDto loanStatementRequestDto);
    void selectLoanOffer(LoanOfferDto loanOfferDto);
    void clientDenied(String statementId);
}
