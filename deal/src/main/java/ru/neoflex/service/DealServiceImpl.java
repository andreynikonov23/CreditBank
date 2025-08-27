package ru.neoflex.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.client.CalculatorApiClient;
import ru.neoflex.dao.DAO;
import ru.neoflex.dto.*;
import ru.neoflex.exceptions.ScoringException;
import ru.neoflex.kafka.KafkaSender;
import ru.neoflex.kafka.TopicName;
import ru.neoflex.model.Client;
import ru.neoflex.model.Credit;
import ru.neoflex.model.Statement;
import ru.neoflex.enums.ApplicationStatus;
import ru.neoflex.enums.ChangeType;
import ru.neoflex.utils.ClientUpdater;
import ru.neoflex.utils.ModelFactory;
import ru.neoflex.utils.StatementUpdater;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DealServiceImpl implements DealService {
    private final KafkaSender kafkaSender;
    private final EmailMessageService emailMessageService;
    private final DAO<Client, UUID> clientDAO;
    private final DAO<Credit, UUID> creditDAO;
    private final DAO<Statement, UUID> statementDAO;
    private final CalculatorApiClient calculatorApiClient;

    public DealServiceImpl(KafkaSender kafkaSender, EmailMessageService emailMessageService, DAO<Client, UUID> clientDAO, DAO<Credit, UUID> creditDAO, DAO<Statement, UUID> statementDAO, CalculatorApiClient calculatorApiClient) {
        this.kafkaSender = kafkaSender;
        this.emailMessageService = emailMessageService;
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
        StatementUpdater.setLoanOfferForStatement(statement, loanOfferDto);
        statementDAO.savaAndFlush(statement);
        log.info("The addition of the loan offer to the application has been completed.");
    }

    @Override
    public void calculate(String statementId, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        log.info("the beginning of the loan processing according to the statement {}", statementId);
        UUID statementUUID = UUID.fromString(statementId);
        Statement statement = statementDAO.findById(statementUUID);
        Client client = statement.getClient();
        ClientUpdater.setFinishDataForClient(client, finishRegistrationRequestDto);
        ScoringDataDto scoringDataDto = ModelFactory.initScoringDataDto(statement);

        CreditDto creditDto = null;
        try {
            creditDto = calculatorApiClient.calc(scoringDataDto);
        } catch (ScoringException e) {
            log.error("data scoring error: {}. The application will be rejected.", e.getMessage());
            StatementUpdater.deniedStatement(statement);
            statementDAO.savaAndFlush(statement);
            throw e;
        }

        Credit credit = ModelFactory.createCreditByDto(creditDto);
        creditDAO.savaAndFlush(credit);
        StatementUpdater.setCreditForStatement(statement, credit);
        statementDAO.savaAndFlush(statement);

        EmailMessage message = emailMessageService.collectResultCreditCalcMessage(statement);
        kafkaSender.sendMessage(message, TopicName.CREATE_DOCUMENTS);
        log.info("credit processing completed");
    }
}
