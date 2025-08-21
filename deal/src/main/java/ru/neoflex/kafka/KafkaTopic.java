package ru.neoflex.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {
    @Bean
    public NewTopic finishRegistrationTopic() {
        return TopicBuilder.name(TopicName.FINISH_REGISTRATION.toString()).build();
    }

    @Bean
    public NewTopic createDocumentsTopic() {
        return TopicBuilder.name(TopicName.CREATE_DOCUMENTS.toString()).build();
    }

    @Bean
    public NewTopic sendDocumentsTopic() {
        return TopicBuilder.name(TopicName.SEND_DOCUMENTS.toString()).build();
    }

    @Bean
    public NewTopic sendSesTopic() {
        return TopicBuilder.name(TopicName.SEND_SES.toString()).build();
    }

    @Bean
    public NewTopic creditIssuedTopic() {
        return TopicBuilder.name(TopicName.CREDIT_ISSUED.toString()).build();
    }

    @Bean
    public NewTopic statementDeniedTopic() {
        return TopicBuilder.name(TopicName.STATEMENT_DENIED.toString()).build();
    }
}
