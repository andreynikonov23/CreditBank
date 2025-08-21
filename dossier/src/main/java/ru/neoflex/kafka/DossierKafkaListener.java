package ru.neoflex.kafka;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.neoflex.dto.EmailMessage;
import ru.neoflex.service.DossierService;

@Service
@Slf4j
public class DossierKafkaListener {
    private final DossierService dossierService;

    public DossierKafkaListener(DossierService dossierService) {
        this.dossierService = dossierService;
    }

    @KafkaListener(topics = {"finish-registration"}, groupId = "dossier", containerFactory = "emailMessageKafkaListenerContainerFactory")
    public void finishRegistrationListener(EmailMessage message) throws MessagingException {
        log.info("Received message [{}] in group1", message);
        dossierService.finishRegistration(message);
    }

    @KafkaListener(topics = {"create-documents"}, groupId = "dossier", containerFactory = "emailMessageKafkaListenerContainerFactory")
    public void createDocumentsListener(EmailMessage message) throws MessagingException {
        log.info("Received message [{}] in group1", message);
        dossierService.createDocuments(message);
    }

    @KafkaListener(topics = {"send-documents"}, groupId = "dossier", containerFactory = "emailMessageKafkaListenerContainerFactory")
    public void sendDocumentsListener(EmailMessage message) throws MessagingException {
        log.info("Received message [{}] in group1", message);
        dossierService.sendDocuments(message);
    }

    @KafkaListener(topics = {"send-ses"}, groupId = "dossier", containerFactory = "emailMessageKafkaListenerContainerFactory")
    public void sendSesListener(EmailMessage message) throws MessagingException {
        log.info("Received message [{}] in group1", message);
        dossierService.sendSes(message);
    }

    @KafkaListener(topics = {"credit-issued"}, groupId = "dossier", containerFactory = "emailMessageKafkaListenerContainerFactory")
    public void creditIssuedListener(EmailMessage message) throws MessagingException {
        log.info("Received message [{}] in group1", message);
        dossierService.creditIssued(message);
    }

    @KafkaListener(topics = {"statement-denied"}, groupId = "dossier", containerFactory = "emailMessageKafkaListenerContainerFactory")
    public void statementDeniedListener(EmailMessage message) throws MessagingException {
        log.info("Received message [{}] in group1", message);
        dossierService.statementDenied(message);
    }
}
