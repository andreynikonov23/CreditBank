package ru.neoflex.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.StatementStatusHistoryDto;
import ru.neoflex.enums.ApplicationStatus;
import ru.neoflex.enums.ChangeType;
import ru.neoflex.model.Credit;
import ru.neoflex.model.Statement;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
public class StatementUpdater {
    public static void setLoanOfferForStatement(Statement statement, LoanOfferDto loanOfferDto) {
        log.debug("adding data about the loan offer {} to the {}", loanOfferDto, statement);
        statement.setStatus(ApplicationStatus.APPROVED);
        statement.setAppliedOffer(loanOfferDto);

        StatementStatusHistoryDto statementStatusHistoryDto = new StatementStatusHistoryDto();
        statementStatusHistoryDto.setStatus("a loan offer has been selected");
        statementStatusHistoryDto.setTime(LocalDate.now());
        statementStatusHistoryDto.setChangeType(ChangeType.AUTOMATIC);
        statement.getStatusHistory().add(statementStatusHistoryDto);
        log.debug("added an entry to the revision history {}", statementStatusHistoryDto);

        log.debug("These statements have been updated {}", statement);
    }

    public static void setCreditForStatement(Statement statement, Credit credit) {
        log.debug("adding data about the credit {} to the {}", credit, statement);
        statement.setCredit(credit);
        statement.setStatus(ApplicationStatus.CC_APPROVED);
        statement.setSignDate(LocalDate.now());

        StatementStatusHistoryDto statementStatusHistoryDto = new StatementStatusHistoryDto();
        statementStatusHistoryDto.setStatus("credit approved");
        statementStatusHistoryDto.setTime(LocalDate.now());
        statementStatusHistoryDto.setChangeType(ChangeType.AUTOMATIC);
        statement.getStatusHistory().add(statementStatusHistoryDto);
        log.debug("added an entry to the revision history {}", statementStatusHistoryDto);

        log.debug("These statements have been updated {}", statement);
    }

    public static void setCreateDocumentStatusStatement(Statement statement) {
        statement.setStatus(ApplicationStatus.DOCUMENT_CREATED);

        StatementStatusHistoryDto statusHistory = new StatementStatusHistoryDto();
        statusHistory.setStatus("documents have been created");
        statusHistory.setTime(LocalDate.now());
        statusHistory.setChangeType(ChangeType.AUTOMATIC);
        statement.getStatusHistory().add(statusHistory);

        log.debug("statement {} has the status DOCUMENT_CREATED", statement.getId());
    }

    public static void generateSesCode(Statement statement) {
        String sesCode = UUID.randomUUID().toString();
        statement.setSesCode(sesCode);

        StatementStatusHistoryDto statusHistory = new StatementStatusHistoryDto();
        statusHistory.setStatus("ses_code is generated");
        statusHistory.setTime(LocalDate.now());
        statusHistory.setChangeType(ChangeType.AUTOMATIC);
        statement.getStatusHistory().add(statusHistory);
        log.debug("ses_code for {} is generated: {}", statement.getId(), sesCode);
    }

    public static void setDocumentSignedStatus(Statement statement) {
        statement.setStatus(ApplicationStatus.DOCUMENT_SIGNED);
        statement.setSignDate(LocalDate.now());

        StatementStatusHistoryDto statusHistory = new StatementStatusHistoryDto();
        statusHistory.setStatus("document signed");
        statusHistory.setChangeType(ChangeType.MANUAL);
        statusHistory.setTime(LocalDate.now());
        statement.getStatusHistory().add(statusHistory);
        log.debug("statement {} has the status DOCUMENT_SIGNED", statement.getId());
    }

    public static void deniedStatement(Statement statement) {
        statement.setStatus(ApplicationStatus.CC_DENIED);

        StatementStatusHistoryDto statementStatusHistoryDto = new StatementStatusHistoryDto();
        statementStatusHistoryDto.setStatus("statement denied");
        statementStatusHistoryDto.setChangeType(ChangeType.AUTOMATIC);
        statementStatusHistoryDto.setTime(LocalDate.now());
        statement.getStatusHistory().add(statementStatusHistoryDto);
        log.debug("statement [{}] - denied", statement);
    }
}
