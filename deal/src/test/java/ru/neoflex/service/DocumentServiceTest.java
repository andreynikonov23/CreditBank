package ru.neoflex.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.dao.DAO;
import ru.neoflex.dto.EmailMessage;
import ru.neoflex.dto.FinishRegistrationRequestDto;
import ru.neoflex.enums.ApplicationStatus;
import ru.neoflex.enums.CreditStatus;
import ru.neoflex.exceptions.ScoringException;
import ru.neoflex.exceptions.SignDocumentException;
import ru.neoflex.exceptions.StatementStatusException;
import ru.neoflex.kafka.KafkaSender;
import ru.neoflex.kafka.TopicName;
import ru.neoflex.model.Client;
import ru.neoflex.model.Statement;
import ru.neoflex.utils.TestData;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/init_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class DocumentServiceTest {
    @Autowired
    private DocumentService documentService;
    @Autowired
    private DAO<Statement, UUID> statementDAO;
    @MockBean
    private KafkaSender kafkaSender;

    @Transactional
    @Test
    public void sendDocumentTest() {
        String statementId = "47936ce8-e8c6-496e-a33a-f8f1d4c26c74";

        ArgumentCaptor<EmailMessage> captor = ArgumentCaptor.forClass(EmailMessage.class);

        documentService.sendDocument(statementId);
        Mockito.verify(kafkaSender).sendMessage(captor.capture(), Mockito.eq(TopicName.SEND_DOCUMENTS));

        EmailMessage emailMessage = captor.getValue();
        Statement statement = statementDAO.findById(UUID.fromString(statementId));

        String html = emailMessage.getText();
        assertEquals("xxxkeep3rxxx@gmail.com", emailMessage.getAddress());
        assertTrue(html.contains("<p>Я, <span>Nikonov Andrey Sergeevich</span>"));
        assertTrue(html.contains("<div class=\"data-value\">5000000,00 руб.</div>"));
        assertTrue(html.contains("<form action=\"http://localhost:8080/api/gateway/deal/document/" + statementId + "/sign\" method=\"post\">"));

        assertEquals(ApplicationStatus.DOCUMENT_CREATED, statement.getStatus());
        assertEquals("documents have been created", statement.getStatusHistory().get(statement.getStatusHistory().size()-1).getStatus());
    }

    @Transactional
    @Test
    public void registerSigning() {
        String statementId = "47936ce8-e8c6-496e-a33a-f8f1d4c26c74";

        ArgumentCaptor<EmailMessage> captor = ArgumentCaptor.forClass(EmailMessage.class);

        documentService.registerSigning(statementId);
        Mockito.verify(kafkaSender).sendMessage(captor.capture(), Mockito.eq(TopicName.SEND_SES));

        EmailMessage emailMessage = captor.getValue();
        Statement statement = statementDAO.findById(UUID.fromString(statementId));

        assertNotNull(statement.getSesCode());
        assertEquals("ses_code is generated", statement.getStatusHistory().get(statement.getStatusHistory().size()-1).getStatus());

        String html = emailMessage.getText();
        assertEquals("xxxkeep3rxxx@gmail.com", emailMessage.getAddress());
        assertTrue(html.contains("<p>Ваш кредитный договор №<span>" + statementId + "</span></p>"));
        assertTrue(html.contains("<span class=\"document-value\">2025-08-15</span>"));
        assertTrue(html.contains("<span class=\"document-value\">5000000.00 ₽</span>"));
        assertTrue(html.contains("<span class=\"document-value\">10 лет</span>"));
        assertTrue(html.contains("<span class=\"document-value\">15.00 %</span>"));
        assertTrue(html.contains("<form action=\"http://localhost:8080/api/gateway/deal/document/" + statementId + "/" + statement.getSesCode() + "\" method=\"post\">"));
    }

    @Transactional
    @Test
    public void signDocumentTest() {
        String statementId = "b084413b-9cbc-4791-9732-f827be4a827a";
        String sesCode = "4ee3caf9-b1bb-4e4b-aed0-b0c2bc49151a";
        ArgumentCaptor<EmailMessage> captor = ArgumentCaptor.forClass(EmailMessage.class);

        documentService.signDocument(statementId, sesCode);

        Mockito.verify(kafkaSender).sendMessage(captor.capture(), Mockito.eq(TopicName.CREDIT_ISSUED));
        EmailMessage emailMessage = captor.getValue();

        String html = emailMessage.getText();
        assertEquals("maximov.mv@gmail.com", emailMessage.getAddress());
        assertTrue(html.contains("<div class=\"status-text\">Поздравляем! Ваш кредит успешно оформлен</div>"));
        assertTrue(html.contains("<div class=\"data-value\">3000000,00 руб.</div>"));
        assertTrue(html.contains("<div class=\"data-value\">3584400,00 руб.</div>"));
        assertTrue(html.contains("<div class=\"payment-date\">21.09.2025</div>"));

        Statement statement = statementDAO.findById(UUID.fromString(statementId));
        assertEquals(ApplicationStatus.CREDIT_ISSUES, statement.getStatus());
        assertNotNull(statement.getSignDate());
        assertEquals("document signed", statement.getStatusHistory().get(statement.getStatusHistory().size()-2).getStatus());
        assertEquals("credit issues", statement.getStatusHistory().get(statement.getStatusHistory().size()-1).getStatus());
        assertEquals(CreditStatus.ISSUED, statement.getCredit().getCreditStatus());
    }

    @Transactional
    @Test
    public void signDocumentWithInvalidSesCodeTest() {
        String statementId = "b084413b-9cbc-4791-9732-f827be4a827a";
        String sesCode = "4ee3caf9-b4677-4e4b-aed0-b0c2bc49151a";

        assertThrows(SignDocumentException.class, () -> {
            documentService.signDocument(statementId, sesCode);
        });
    }

    @Test
    public void documentEndpointsForDeniedCredit() {
        String statementId = "0fde9ba9-6735-4029-90d8-39749c676da4";

        assertThrows(StatementStatusException.class, () -> {
            documentService.sendDocument(statementId);
        });

        assertThrows(StatementStatusException.class, () -> {
            documentService.registerSigning(statementId);
        });

        assertThrows(StatementStatusException.class, () -> {
            documentService.signDocument(statementId, UUID.randomUUID().toString());
        });
    }
}
