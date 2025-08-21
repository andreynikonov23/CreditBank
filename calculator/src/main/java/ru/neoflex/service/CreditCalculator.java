package ru.neoflex.service;

import ru.neoflex.dto.CreditDto;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;
import ru.neoflex.dto.ScoringDataDto;
import ru.neoflex.exceptions.ScoringException;

import java.util.List;

public interface CreditCalculator {
    List<LoanOfferDto> calculateLoanTerms(LoanStatementRequestDto requestDto);
    CreditDto calc(ScoringDataDto dataDto) throws ScoringException;
}
