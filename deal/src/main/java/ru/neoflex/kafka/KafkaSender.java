package ru.neoflex.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.neoflex.dto.EmailMessage;

@Service
@Slf4j
public class KafkaSender {
    private final KafkaTemplate<String, EmailMessage> kafkaTemplate;

    public KafkaSender(KafkaTemplate<String, EmailMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(EmailMessage message, TopicName topicName) {
        log.debug("sending a message to kafka: topic - {}, message - {}", topicName, message);
        kafkaTemplate.send(topicName.toString(), message);
    }
}
