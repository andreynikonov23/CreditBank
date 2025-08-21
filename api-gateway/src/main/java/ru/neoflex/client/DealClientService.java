package ru.neoflex.client;

import ru.neoflex.dto.FinishRegistrationRequestDto;

public interface DealClientService extends ApiClient{
    void calculate(String statementId, FinishRegistrationRequestDto finishRegistrationRequestDto);
    void requestSendDocuments(String statementId);
    void requestSignDocuments(String statementId);
    void signDocument(String statementId, String sesCode);
    String findStatementById(String statementId);
    String getAllStatements();
}
