package ru.neoflex.calculator.service;

import ru.neoflex.calculator.dto.ScoringDataDto;
import ru.neoflex.calculator.exceptions.ScoringException;

import java.math.BigDecimal;

public interface Scoring {
    BigDecimal scoreRate(BigDecimal rate, ScoringDataDto scoringDataDto) throws ScoringException;
}
