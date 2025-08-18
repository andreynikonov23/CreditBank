package ru.neoflex.deal.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.deal.client.CalculatorApiClient;
import ru.neoflex.deal.dao.DAO;
import ru.neoflex.deal.model.Client;
import ru.neoflex.deal.model.Credit;
import ru.neoflex.deal.model.Statement;
import ru.neoflex.deal.model.dto.*;
import ru.neoflex.deal.model.enums.ApplicationStatus;
import ru.neoflex.deal.model.enums.ChangeType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DealServiceImpl implements DealService {
    private final DAO<Client, UUID> clientDAO;
    private final DAO<Credit, UUID> creditDAO;
    private final DAO<Statement, UUID> statementDAO;
    private final CalculatorApiClient calculatorApiClient;

    public DealServiceImpl(DAO<Client, UUID> clientDAO, DAO<Credit, UUID> creditDAO, DAO<Statement, UUID> statementDAO, CalculatorApiClient calculatorApiClient) {
        this.clientDAO = clientDAO;
        this.creditDAO = creditDAO;
        this.statementDAO = statementDAO;
        this.calculatorApiClient = calculatorApiClient;
    }

    @Override
    public List<LoanOfferDto> calcLoanTerms(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("start calculating loan terms {}", loanStatementRequestDto);
        List<LoanOfferDto> loanOffers = calculatorApiClient.offers(loanStatementRequestDto);

        Client client = ModelFactory.createClientByLoanStatementRequestDto(loanStatementRequestDto);
        clientDAO.savaAndFlush(client);

        Statement statement = ModelFactory.initStatement(client);
        statementDAO.savaAndFlush(statement);

        loanOffers.forEach(loanOfferDto -> loanOfferDto.setStatementId(statement.getId()));

        log.info("The loan terms have been calculated, and {} offers have been found.", loanOffers.size());
        return loanOffers;
    }

    @Override
    public void selectLoanOffer(LoanOfferDto loanOfferDto) {
        log.info("the beginning of adding data about the loan offer {}", loanOfferDto);
        UUID statementId = loanOfferDto.getStatementId();
        Statement statement = statementDAO.findById(statementId);
        setLoanOfferForStatement(statement, loanOfferDto);
        statementDAO.savaAndFlush(statement);
        log.info("The addition of the loan offer to the application has been completed.");
    }

    @Override
    public void calculate(String statementId, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        log.info("the beginning of the loan processing according to the statement {}", statementId);
        UUID statementUUID = UUID.fromString(statementId);
        Statement statement = statementDAO.findById(statementUUID);
        Client client = statement.getClient();
        setFinishDataForClient(client, finishRegistrationRequestDto);
        clientDAO.savaAndFlush(client);
        ScoringDataDto scoringDataDto = ModelFactory.initScoringDataDto(statement);
        CreditDto creditDto =  calculatorApiClient.calc(scoringDataDto);
        Credit credit = ModelFactory.createCreditByDto(creditDto);
        creditDAO.savaAndFlush(credit);
        setCreditForStatement(statement, credit);
        statementDAO.savaAndFlush(statement);
        log.info("credit processing completed");
    }

    private void setLoanOfferForStatement(Statement statement, LoanOfferDto loanOfferDto) {
        log.debug("adding data about the loan offer {} to the {}", loanOfferDto, statement);
        statement.setStatus(ApplicationStatus.PREPARE_DOCUMENTS);
        statement.setAppliedOffer(loanOfferDto);

        StatementStatusHistoryDto statementStatusHistoryDto = new StatementStatusHistoryDto();
        statementStatusHistoryDto.setStatus("a loan offer has been selected");
        statementStatusHistoryDto.setTime(LocalDate.now());
        statementStatusHistoryDto.setChangeType(ChangeType.AUTOMATIC);
        statement.getStatusHistory().add(statementStatusHistoryDto);
        log.debug("added an entry to the revision history {}", statementStatusHistoryDto);

        log.debug("These statements have been updated {}", statement);
    }

    private void setCreditForStatement(Statement statement, Credit credit) {
        log.debug("adding data about the credit {} to the {}", credit, statement);
        statement.setCredit(credit);
        statement.setStatus(ApplicationStatus.APPROVED);
        statement.setSignDate(LocalDate.now());

        StatementStatusHistoryDto statementStatusHistoryDto = new StatementStatusHistoryDto();
        statementStatusHistoryDto.setStatus("credit approved");
        statementStatusHistoryDto.setTime(LocalDate.now());
        statementStatusHistoryDto.setChangeType(ChangeType.AUTOMATIC);
        statement.getStatusHistory().add(statementStatusHistoryDto);
        log.debug("added an entry to the revision history {}", statementStatusHistoryDto);

        log.debug("These statements have been updated {}", statement);
    }

    private void setFinishDataForClient(Client client, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        log.debug("completion of client registration {} with final data {}", client, finishRegistrationRequestDto);
        client.setGender(finishRegistrationRequestDto.getGender());
        client.setMaritalStatus(finishRegistrationRequestDto.getMaritalStatus());
        client.setDependentAmount(finishRegistrationRequestDto.getDependentAmount());
        client.getPassportDto().setIssueDate(finishRegistrationRequestDto.getPassportIssueDate());
        client.getPassportDto().setIssueBranch(finishRegistrationRequestDto.getPassportIssueBranch());
        client.setEmploymentDto(finishRegistrationRequestDto.getEmploymentDto());
        client.setAccountNumber(finishRegistrationRequestDto.getAccountNumber());
        log.debug("the client's registration data has been entered: {}", client);
    }
}
