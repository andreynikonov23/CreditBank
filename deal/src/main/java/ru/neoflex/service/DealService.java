package ru.neoflex.service;

import ru.neoflex.dto.FinishRegistrationRequestDto;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDto> calcLoanTerms(LoanStatementRequestDto loanStatementRequestDto);
    void selectLoanOffer(LoanOfferDto loanOfferDto);
    void calculate(String statementId, FinishRegistrationRequestDto finishRegistrationRequestDto);
}
