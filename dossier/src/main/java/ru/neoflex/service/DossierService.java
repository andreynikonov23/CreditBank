package ru.neoflex.service;

import jakarta.mail.MessagingException;
import ru.neoflex.dto.EmailMessage;

public interface DossierService {
    void finishRegistration(EmailMessage message) throws MessagingException;
    void createDocuments(EmailMessage message) throws MessagingException;
    void sendDocuments(EmailMessage message) throws MessagingException;
    void sendSes(EmailMessage message) throws MessagingException;
    void creditIssued(EmailMessage message) throws MessagingException;
    void statementDenied(EmailMessage message) throws MessagingException;
}
