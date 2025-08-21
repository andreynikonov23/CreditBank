package ru.neoflex.kafka;

public enum TopicName {
    FINISH_REGISTRATION("finish-registration"),
    CREATE_DOCUMENTS("create-documents"),
    SEND_DOCUMENTS("send-documents"),
    SEND_SES("send-ses"),
    CREDIT_ISSUED("credit-issued"),
    STATEMENT_DENIED("statement-denied");

    private final String name;

    TopicName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
