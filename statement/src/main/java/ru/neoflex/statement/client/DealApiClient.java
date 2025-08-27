package ru.neoflex.statement.client;

import ru.neoflex.statement.dto.FinishRegistrationRequestDto;
import ru.neoflex.statement.dto.LoanOfferDto;
import ru.neoflex.statement.dto.LoanStatementRequestDto;

import java.util.List;

public interface DealApiClient {
    List<LoanOfferDto> statement(LoanStatementRequestDto loanStatementRequestDto);
    void select(LoanOfferDto loanOfferDto);
}
