package ru.neoflex.deal.client;

import ru.neoflex.deal.model.dto.CreditDto;
import ru.neoflex.deal.model.dto.LoanOfferDto;
import ru.neoflex.deal.model.dto.LoanStatementRequestDto;
import ru.neoflex.deal.model.dto.ScoringDataDto;

import java.util.List;

public interface CalculatorApiClient {
    List<LoanOfferDto> offers(LoanStatementRequestDto loanStatementRequestDto);
    CreditDto calc(ScoringDataDto scoringDataDto);
}
