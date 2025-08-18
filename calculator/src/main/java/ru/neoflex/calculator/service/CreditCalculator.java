package ru.neoflex.calculator.service;

import ru.neoflex.calculator.dto.CreditDto;
import ru.neoflex.calculator.dto.LoanOfferDto;
import ru.neoflex.calculator.dto.LoanStatementRequestDto;
import ru.neoflex.calculator.dto.ScoringDataDto;
import ru.neoflex.calculator.exceptions.ScoringException;

import java.util.List;

public interface CreditCalculator {
    List<LoanOfferDto> calculateLoanTerms(LoanStatementRequestDto requestDto);
    CreditDto calc(ScoringDataDto dataDto) throws ScoringException;
}
