package ru.neoflex.service;

import ru.neoflex.dto.ScoringDataDto;
import ru.neoflex.exceptions.ScoringException;

import java.math.BigDecimal;

public interface Scoring {
    BigDecimal scoreRate(BigDecimal rate, ScoringDataDto scoringDataDto) throws ScoringException;
}
