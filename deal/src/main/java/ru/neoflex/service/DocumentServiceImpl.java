package ru.neoflex.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.dao.DAO;
import ru.neoflex.dto.EmailMessage;
import ru.neoflex.enums.ApplicationStatus;
import ru.neoflex.enums.CreditStatus;
import ru.neoflex.exceptions.SignDocumentException;
import ru.neoflex.exceptions.StatementStatusException;
import ru.neoflex.kafka.KafkaSender;
import ru.neoflex.kafka.TopicName;
import ru.neoflex.model.Statement;
import ru.neoflex.utils.StatementUpdater;

import java.util.UUID;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {
    private final KafkaSender kafkaSender;
    private final EmailMessageService emailMessageService;
    private final DAO<Statement, UUID> statementDAO;

    public DocumentServiceImpl(KafkaSender kafkaSender, EmailMessageService emailMessageService, DAO<Statement, UUID> statementDAO) {
        this.kafkaSender = kafkaSender;
        this.emailMessageService = emailMessageService;
        this.statementDAO = statementDAO;
    }

    @Override
    public void sendDocument(String statementId) {
        log.info("the beginning of the generation of documents on the statement {}", statementId);
        Statement statement = statementDAO.findById(UUID.fromString(statementId));

        if (statement.getStatus().equals(ApplicationStatus.CLIENT_DENIED) || statement.getStatus().equals(ApplicationStatus.CC_DENIED)) {
            String errorMessage = "statement has denied";
            log.error(errorMessage);
            throw new StatementStatusException(errorMessage);
        }

        EmailMessage message = emailMessageService.collectDocumentMessage(statement);
        kafkaSender.sendMessage(message, TopicName.SEND_DOCUMENTS);
        StatementUpdater.setCreateDocumentStatusStatement(statement);
        statementDAO.savaAndFlush(statement);
    }

    @Override
    public void registerSigning(String statementId) {
        log.info("start of data generation for signing the document: statement {}", statementId);
        Statement statement = statementDAO.findById(UUID.fromString(statementId));

        if (statement.getStatus().equals(ApplicationStatus.CLIENT_DENIED) || statement.getStatus().equals(ApplicationStatus.CC_DENIED)) {
            String errorMessage = "statement has denied";
            log.error(errorMessage);
            throw new StatementStatusException(errorMessage);
        }

        StatementUpdater.generateSesCode(statement);
        statementDAO.savaAndFlush(statement);
        EmailMessage message = emailMessageService.collectSignedMessage(statement);
        kafkaSender.sendMessage(message, TopicName.SEND_SES);
    }

    @Override
    public void signDocument(String statementId, String sesCode) {
        log.info("the beginning of the document signing: statement {}, sesCode: {}", statementId, sesCode);
        Statement statement = statementDAO.findById(UUID.fromString(statementId));

        if (statement.getStatus().equals(ApplicationStatus.CLIENT_DENIED) || statement.getStatus().equals(ApplicationStatus.CC_DENIED)) {
            String errorMessage = "statement has denied";
            log.error(errorMessage);
            throw new StatementStatusException(errorMessage);
        }

        if (!(statement.getSesCode().equals(sesCode))) {
            throw new SignDocumentException("not matching ses_codes");
        }

        StatementUpdater.setCreditIssuesStatus(statement);
        statement.getCredit().setCreditStatus(CreditStatus.ISSUED);

        statementDAO.savaAndFlush(statement);

        EmailMessage emailMessage = emailMessageService.collectCreditIssuedMessage(statement);
        kafkaSender.sendMessage(emailMessage, TopicName.CREDIT_ISSUED);

        log.debug("The documents for statement {} have been signed: {}", statement.getId(), statement.getCredit());
    }
}
