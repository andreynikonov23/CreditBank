package ru.neoflex.service;

import ru.neoflex.dto.EmailMessage;
import ru.neoflex.model.Statement;


public interface EmailMessageService {
    EmailMessage collectFinishRegistrationMessage(Statement statement);
    EmailMessage collectResultCreditCalcMessage(Statement statement);
    EmailMessage collectDocumentMessage(Statement statement);
    EmailMessage collectSignedMessage(Statement statement);
    EmailMessage collectCreditIssuedMessage(Statement statement);
    EmailMessage collectStatementDeniedMessage(Statement statement);
}
