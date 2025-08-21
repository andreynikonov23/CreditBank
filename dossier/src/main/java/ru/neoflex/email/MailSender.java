package ru.neoflex.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.neoflex.dto.EmailMessage;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class MailSender {
    @Value("${spring.mail.username}")
    private String emailSender;
    private final JavaMailSender javaMailSender;

    public MailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendHtmlMessage(EmailMessage message) throws MessagingException {
        log.debug("preparing to send a message");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.name()
        );
        helper.setTo(message.getAddress());
        helper.setSubject(message.getTheme().getName());
        helper.setText(message.getText(), true);
        helper.setFrom(emailSender);

        log.trace("sending a message {}", message);
        try {
            javaMailSender.send(mimeMessage);
            log.debug("the sending of the message was successful");
        } catch (MailException ex) {
            log.error("error sending the message: {}", ex.getMessage());
        }

    }
}
