package ru.neoflex.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.neoflex.dto.FinishRegistrationRequestDto;

@Service
@Slf4j
public class DealClientSerivceImpl implements DealClientService{
    private final RestClient restClient;
    @Value("${creditbank.deal.host}")
    private String uri;

    public DealClientSerivceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void calculate(String statementId, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        String endpoint = "/calculate?statementId=" + statementId;

        log.trace("the beginning of sending a request to {}{} MS deal", uri, endpoint);
        restClient.post()
                .uri(uri + endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(finishRegistrationRequestDto)
                .retrieve()
                .toBodilessEntity();
        log.debug("the request was successfully {}{} offers executed.", uri, endpoint);
    }

    @Override
    public void requestSendDocuments(String statementId) {
        String endpoint = "/document/" + statementId + "/send";

        log.trace("the beginning of sending a request to {}{} MS deal", uri, endpoint);
        restClient.post()
                .uri(uri + endpoint)
                .retrieve()
                .toBodilessEntity();
        log.debug("the request was successfully {}{}.", uri, endpoint);
    }

    @Override
    public void requestSignDocuments(String statementId) {
        String endpoint = "/document/" + statementId + "/sign";

        log.trace("the beginning of sending a request to {}{} MS deal", uri, endpoint);
        restClient.post()
                .uri(uri + endpoint)
                .retrieve()
                .toBodilessEntity();
        log.debug("the request was successfully {}{}.", uri, endpoint);
    }

    @Override
    public void signDocument(String statementId, String sesCode) {
        String endpoint = "/document/" + statementId + "/" + sesCode;

        log.trace("the beginning of sending a request to {}{} MS deal", uri, endpoint);
        restClient.post()
                .uri(uri + endpoint)
                .retrieve()
                .toBodilessEntity();
        log.debug("the request was successfully {}{}.", uri, endpoint);
    }

    @Override
    public void clientDenied(String statementId) {
        String endpoint = "/document/" + statementId + "/denied";
        log.trace("the beginning of sending a request to {}{} MS deal", uri, endpoint);
        restClient.post()
                .uri(uri + endpoint)
                .retrieve()
                .toBodilessEntity();
        log.debug("the request was successfully {}{}.", uri, endpoint);
    }

    @Override
    public String findStatementById(String statementId) {
        String endpoint = "/admin/statement/" + statementId;
        log.trace("the beginning of sending a request to {}{} MS deal", uri, endpoint);

        String statementsJson = restClient.get()
                .uri(uri + endpoint)
                .retrieve()
                .toEntity(String.class)
                .getBody();

        log.debug("the request was successfully {}{}.", uri, endpoint);
        return statementsJson;
    }

    @Override
    public String getAllStatements() {
        String endpoint = "/admin/statement";
        log.trace("the beginning of sending a request to {}{} MS deal", uri, endpoint);

        String statementsJson = restClient.get()
                .uri(uri + endpoint)
                .retrieve()
                .toEntity(String.class)
                .getBody();

        log.debug("the request was successfully {}{}.", uri, endpoint);
        return statementsJson;
    }

}
