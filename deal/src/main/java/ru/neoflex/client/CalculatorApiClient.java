package ru.neoflex.client;

import ru.neoflex.dto.CreditDto;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;
import ru.neoflex.dto.ScoringDataDto;

import java.util.List;

public interface CalculatorApiClient {
    List<LoanOfferDto> offers(LoanStatementRequestDto loanStatementRequestDto);
    CreditDto calc(ScoringDataDto scoringDataDto);
}
