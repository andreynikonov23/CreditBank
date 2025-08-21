package ru.neoflex.service;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.dto.EmailMessage;
import ru.neoflex.email.MailSender;

@Service
@Slf4j
public class DossierServiceImpl implements DossierService{
    private final MailSender mailSender;

    public DossierServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void finishRegistration(EmailMessage message) throws MessagingException {
        mailSender.sendHtmlMessage(message);
    }

    @Override
    public void createDocuments(EmailMessage message) throws MessagingException {
        mailSender.sendHtmlMessage(message);
    }

    @Override
    public void sendDocuments(EmailMessage message) throws MessagingException {
        mailSender.sendHtmlMessage(message);
    }

    @Override
    public void sendSes(EmailMessage message) throws MessagingException {
        mailSender.sendHtmlMessage(message);
    }

    @Override
    public void creditIssued(EmailMessage message) throws MessagingException {
        mailSender.sendHtmlMessage(message);
    }

    @Override
    public void statementDenied(EmailMessage message) throws MessagingException {
        mailSender.sendHtmlMessage(message);
    }
}
