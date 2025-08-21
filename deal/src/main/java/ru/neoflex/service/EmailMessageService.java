package ru.neoflex.service;

import ru.neoflex.dto.EmailMessage;
import ru.neoflex.model.Statement;


public interface EmailMessageService {
    EmailMessage collectResultCreditCalcMessage(Statement statement);
    EmailMessage collectDocumentMessage(Statement statement);
    EmailMessage collectSignedMessage(Statement statement);
}
