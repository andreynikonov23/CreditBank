package ru.neoflex.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.neoflex.dto.EmailMessage;
import ru.neoflex.enums.MessageTheme;
import ru.neoflex.model.Statement;

@Service
@Slf4j
public class EmailHtmlMessageServiceImpl implements EmailMessageService{
    private final TemplateEngine templateEngine;

    public EmailHtmlMessageServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public EmailMessage collectFinishRegistrationMessage(Statement statement) {
        log.info("preparing to create a registration completion notification for the client - {}", statement);
        Context context = new Context();
        context.setVariable("statement", statement);

        String body = generateMessageBodyHtml(context, "finish-registration-notification");
        EmailMessage emailMessage = new EmailMessage(
                statement.getClient().getEmail(),
                MessageTheme.FINISH_REGISTRATION,
                statement.getId().toString(),
                body
        );
        log.debug("email is generated: {}", emailMessage);
        return emailMessage;
    }

    @Override
    public EmailMessage collectResultCreditCalcMessage(Statement statement) {
        log.info("preparing to create a message about the result of calculating the loan for the application {}", statement);
        Context context = new Context();
        context.setVariable("statement", statement);
        context.setVariable("documentsRequestUrl", "http://localhost:8080/api/gateway/deal/document/" + statement.getId() + "/send");

        String body = generateMessageBodyHtml(context, "prepare-docs-message");
        EmailMessage emailMessage = new EmailMessage(
                statement.getClient().getEmail(),
                MessageTheme.RESULT_CREDIT_CALC,
                statement.getId().toString(),
                body
        );
        log.debug("email is generated: {}", emailMessage);
        return emailMessage;
    }

    @Override
    public EmailMessage collectDocumentMessage(Statement statement) {
        log.info("preparation for the creation of a credit agreement application {}", statement);
        Context context = new Context();
        context.setVariable("statement", statement);
        context.setVariable("signingRequestUrl", "http://localhost:8080/api/gateway/deal/document/" + statement.getId() + "/sign");
        context.setVariable("deniedUrl", "http://localhost:8080/api/gateway/deal/document/" + statement.getId() + "/denied");

        String body = generateMessageBodyHtml(context, "document-message");
        EmailMessage emailMessage = new EmailMessage(
                statement.getClient().getEmail(),
                MessageTheme.CREDIT_AGREEMENT,
                statement.getId().toString(),
                body
        );
        log.debug("email is generated: {}", emailMessage);
        return emailMessage;
    }

    @Override
    public EmailMessage collectSignedMessage(Statement statement) {
        log.info("preparing to create an email for signature registration on the application {}", statement);
        Context context = new Context();
        context.setVariable("statement", statement);
        context.setVariable("signUrl", "http://localhost:8080/api/gateway/deal/document/" + statement.getId() + "/" + statement.getSesCode());

        String body = generateMessageBodyHtml(context, "sign-document-message");
        EmailMessage emailMessage = new EmailMessage(
                statement.getClient().getEmail(),
                MessageTheme.SIGNATURE_REGISTRATION,
                statement.getId().toString(),
                body
        );
        log.debug("email is generated: {}", emailMessage);
        return emailMessage;
    }

    @Override
    public EmailMessage collectCreditIssuedMessage(Statement statement) {
        log.info("preparing to generate a credit issues message for statement {}", statement);
        Context context = new Context();
        context.setVariable("statement", statement);
        context.setVariable("credit", statement.getCredit());

        String body = generateMessageBodyHtml(context, "credit-issued-message");
        EmailMessage emailMessage = new EmailMessage(
                statement.getClient().getEmail(),
                MessageTheme.CREDIT_ISSUED,
                statement.getId().toString(),
                body
        );
        log.debug("email is generated: {}", emailMessage);
        return emailMessage;
    }

    @Override
    public EmailMessage collectStatementDeniedMessage(Statement statement) {
        log.info("preparing to generate a credit denied message for statement {}", statement);
        Context context = new Context();
        context.setVariable("statement", statement);

        String body = generateMessageBodyHtml(context, "statement-denied-notification");
        EmailMessage emailMessage = new EmailMessage(
                statement.getClient().getEmail(),
                MessageTheme.STATEMENT_DENIED,
                statement.getId().toString(),
                body
        );
        log.debug("email is generated: {}", emailMessage);
        return emailMessage;
    }

    private String generateMessageBodyHtml(Context context, String template) {
        String html = templateEngine.process(template, context);
        log.debug("the html body of the letter is formed based on the context {}", context.getVariableNames());
        return html;
    }
}
