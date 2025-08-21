package ru.neoflex.service;

public interface DocumentService {
    void sendDocument(String statementId);
    void registerSigning(String statementId);
    void signDocument(String statementId, String sesCode);
}
