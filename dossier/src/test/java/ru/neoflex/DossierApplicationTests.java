package ru.neoflex;

import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import ru.neoflex.dto.EmailMessage;
import ru.neoflex.enums.MessageTheme;
import ru.neoflex.kafka.DossierKafkaListener;
import ru.neoflex.service.DossierService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
@TestPropertySource("/application-local.properties")
class DossierApplicationTests {
	@Autowired
	private DossierKafkaListener kafkaListener;
	@Autowired
	private DossierService dossierService;
	@MockitoSpyBean
	private JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String emailSender;
	private EmailMessage testEmailMessage;

	@BeforeEach
	void init() {
		testEmailMessage = new EmailMessage();
		testEmailMessage.setAddress("johndoe@mail.com");
		testEmailMessage.setStatementId(UUID.randomUUID().toString());

		Mockito.doNothing().when(javaMailSender).send(any(MimeMessage.class));
	}

	@Test
	void createDocumentTest() throws MessagingException, IOException {
		testEmailMessage.setTheme(MessageTheme.RESULT_CREDIT_CALC);
		testEmailMessage.setText("Your credit calculating result ...");

		ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);

		kafkaListener.createDocumentsListener(testEmailMessage);
		Mockito.verify(javaMailSender).send(captor.capture());
		MimeMessage sendingMessage = captor.getValue();

		assertMimeMessage(sendingMessage);
	}

	@Test
	void sendDocumentTest() throws MessagingException, IOException {
		testEmailMessage.setTheme(MessageTheme.CREDIT_AGREEMENT);
		testEmailMessage.setText("Your credit agreement ...");

		ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);

		kafkaListener.sendDocumentsListener(testEmailMessage);
		Mockito.verify(javaMailSender).send(captor.capture());
		MimeMessage sendingMessage = captor.getValue();

		assertMimeMessage(sendingMessage);
	}

	@Test
	void sendSesTest() throws MessagingException, IOException {
		testEmailMessage.setTheme(MessageTheme.SIGNATURE_REGISTRATION);
		testEmailMessage.setText("Sign documents ...");

		ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);

		kafkaListener.sendSesListener(testEmailMessage);
		Mockito.verify(javaMailSender).send(captor.capture());
		MimeMessage sendingMessage = captor.getValue();

		assertMimeMessage(sendingMessage);
	}

	private void assertMimeMessage(MimeMessage sendingMessage) throws MessagingException, UnsupportedEncodingException {
		Address[] fromAddresses = sendingMessage.getFrom();

		assertNotNull(fromAddresses);
		assertTrue(fromAddresses.length > 0);
		InternetAddress internetAddressFrom = (InternetAddress) fromAddresses[0];
		assertEquals(emailSender, internetAddressFrom.getAddress());

		Address[] toAddresses = sendingMessage.getRecipients(Message.RecipientType.TO);
		assertNotNull(toAddresses);
		InternetAddress internetAddressTo = (InternetAddress) toAddresses[0];
		assertEquals(testEmailMessage.getAddress(), internetAddressTo.getAddress());

		String encodeSubject = sendingMessage.getSubject();
		String subject = MimeUtility.decodeText(encodeSubject);
		assertEquals(testEmailMessage.getTheme().getName(), subject);
	}
}
