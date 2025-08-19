package ru.neoflex.deal.service;

import lombok.extern.slf4j.Slf4j;
import ru.neoflex.deal.model.Client;
import ru.neoflex.deal.model.Credit;
import ru.neoflex.deal.model.Statement;
import ru.neoflex.deal.model.dto.*;
import ru.neoflex.deal.model.enums.ApplicationStatus;
import ru.neoflex.deal.model.enums.ChangeType;
import ru.neoflex.deal.model.enums.CreditStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ModelFactory {
    public static Client createClientByLoanStatementRequestDto(LoanStatementRequestDto loanStatementRequestDto) {
        log.debug("creating Client by {}", loanStatementRequestDto);
        Client client = new Client();
        client.setFirstname(loanStatementRequestDto.getFirstname());
        client.setLastname(loanStatementRequestDto.getLastname());
        client.setMiddleName(loanStatementRequestDto.getMiddleName());
        client.setBirthdate(loanStatementRequestDto.getBirthdate());
        client.setEmail(loanStatementRequestDto.getEmail());

        PassportDto passportDto = new PassportDto();
        passportDto.setPassportUUID(UUID.randomUUID());
        passportDto.setSeries(loanStatementRequestDto.getPassportSeries());
        passportDto.setNumber(loanStatementRequestDto.getPassportNumber());
        client.setPassportDto(passportDto);

        log.debug("Client has been created {}", client);
        return client;
    }

    public static Statement initStatement(Client client) {
        log.debug("start init statement by {}", client);
        Statement statement = new Statement();
        statement.setStatus(ApplicationStatus.DOCUMENT_CREATED);
        statement.setCreationDate(LocalDate.now());
        statement.setClient(client);

        StatementStatusHistoryDto statusHistoryDto = new StatementStatusHistoryDto();
        statusHistoryDto.setStatus("create statement");
        statusHistoryDto.setTime(LocalDate.now());
        statusHistoryDto.setChangeType(ChangeType.AUTOMATIC);
        List<StatementStatusHistoryDto> statusHistory = List.of(statusHistoryDto);
        statement.setStatusHistory(statusHistory);

        log.debug("Statement has been created {}", statement);
        return statement;
    }

    public static ScoringDataDto initScoringDataDto(Statement statement) {
        log.debug("start init ScoringDataDto by {}", statement);
        Client client = statement.getClient();
        LoanOfferDto loanOfferDto = statement.getAppliedOffer();

        ScoringDataDto scoringDataDto = new ScoringDataDto();
        scoringDataDto.setAmount(statement.getAppliedOffer().getRequestedAmount());
        scoringDataDto.setTerm(statement.getAppliedOffer().getTerm());
        scoringDataDto.setFirstname(client.getFirstname());
        scoringDataDto.setLastname(client.getLastname());
        scoringDataDto.setMiddleName(client.getMiddleName());
        scoringDataDto.setGender(client.getGender());
        scoringDataDto.setBirthdate(client.getBirthdate());
        scoringDataDto.setPassportSeries(client.getPassportDto().getSeries());
        scoringDataDto.setPassportNumber(client.getPassportDto().getNumber());
        scoringDataDto.setPassportIssueDate(client.getPassportDto().getIssueDate());
        scoringDataDto.setPassportIssueBranch(client.getPassportDto().getIssueBranch());
        scoringDataDto.setMaritalStatus(client.getMaritalStatus());
        scoringDataDto.setEmployment(client.getEmploymentDto());
        scoringDataDto.setDependentAmount(client.getDependentAmount());
        scoringDataDto.setAccountNumber(client.getAccountNumber());
        scoringDataDto.setInsuranceEnabled(loanOfferDto.isInsuranceEnabled());
        scoringDataDto.setSalaryClient(loanOfferDto.isSalaryClient());

        log.debug("ScoringDataDto has been created {}", scoringDataDto);
        return scoringDataDto;
    }

    public static Credit createCreditByDto(CreditDto creditDto) {
        log.debug("start init Credit by {}", creditDto);
        Credit credit = new Credit();
        credit.setAmount(creditDto.getAmount());
        credit.setTerm(creditDto.getTerm());
        credit.setMonthlyPayment(creditDto.getMonthlyPayment());
        credit.setRate(creditDto.getRate());
        credit.setPsk(creditDto.getPsk());
        credit.setPaymentSchedule(creditDto.getPaymentSchedule());
        credit.setInsuranceEnable(creditDto.isInsuranceEnabled());
        credit.setSalaryClient(creditDto.isSalaryClient());
        credit.setCreditStatus(CreditStatus.CALCULATED);

        log.debug("Credit has been created {}", creditDto);
        return credit;
    }
}
