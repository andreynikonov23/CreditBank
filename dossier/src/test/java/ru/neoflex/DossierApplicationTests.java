package ru.neoflex;

import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

	@ParameterizedTest
	@CsvSource({
			"FINISH_REGISTRATION, 'Please complete the registration', finishRegistrationListener",
			"RESULT_CREDIT_CALC, 'Your credit calculating result ...', createDocumentsListener",
			"CREDIT_AGREEMENT, 'Your credit agreement ...', sendDocumentsListener",
			"SIGNATURE_REGISTRATION, 'The signature is completed', sendSesListener",
			"CREDIT_ISSUED, 'credit issued', creditIssuedListener",
			"STATEMENT_DENIED, 'Your statement has been rejected', statementDeniedListener"
	})
	void sendMessageTest(String themeText, String text, String methodName) throws MessagingException, UnsupportedEncodingException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		MessageTheme theme = MessageTheme.valueOf(themeText);
		testEmailMessage.setTheme(theme);
		testEmailMessage.setText(text);

		ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);

		Method method = kafkaListener.getClass().getMethod(methodName, EmailMessage.class);
		method.invoke(kafkaListener, testEmailMessage);

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
