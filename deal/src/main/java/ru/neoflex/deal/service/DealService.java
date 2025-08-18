package ru.neoflex.deal.service;

import ru.neoflex.deal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.deal.model.dto.LoanOfferDto;
import ru.neoflex.deal.model.dto.LoanStatementRequestDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDto> calcLoanTerms(LoanStatementRequestDto loanStatementRequestDto);
    void selectLoanOffer(LoanOfferDto loanOfferDto);
    void calculate(String statementId, FinishRegistrationRequestDto finishRegistrationRequestDto);
}
