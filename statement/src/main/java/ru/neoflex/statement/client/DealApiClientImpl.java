package ru.neoflex.statement.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.neoflex.statement.dto.FinishRegistrationRequestDto;
import ru.neoflex.statement.dto.LoanOfferDto;
import ru.neoflex.statement.dto.LoanStatementRequestDto;

import java.util.List;

@Service
@Slf4j
public class DealApiClientImpl implements DealApiClient{
    private final RestClient restClient = RestClient.create();
    private final String dealUri = "http://localhost:8082/deal/";


    @Override
    public List<LoanOfferDto> statement(LoanStatementRequestDto loanStatementRequestDto) {
        String endpoint = "statement";
        log.trace("the beginning of sending a request to {}{} MS deal", dealUri, endpoint);

        List<LoanOfferDto> loanOffers = restClient.post()
                .uri(dealUri + endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(loanStatementRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        ((request, response) -> {
                            HttpStatusCode status = response.getStatusCode();
                            String statusText = response.getStatusText();
                            log.error("request sending error {}{}: Error {} -> {}", dealUri, endpoint, status, statusText);
                            throw new HttpClientErrorException(status, statusText);
                        }))
                .body(new ParameterizedTypeReference<>() {});

        log.debug("the request was successfully {}{} offers executed. Result: {}: {} size", dealUri, endpoint, loanOffers.getClass(), loanOffers.size());
        return loanOffers;
    }

    @Override
    public void select(LoanOfferDto loanOfferDto) {
        String endpoint = "select";

        log.trace("the beginning of sending a request to {}{} MS deal", dealUri, endpoint);
        restClient.post()
                .uri(dealUri + endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(loanOfferDto)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        ((request, response) -> {
                            HttpStatusCode status = response.getStatusCode();
                            String statusText = response.getStatusText();
                            log.error("request sending error {}{}: Error {} -> {}", dealUri, endpoint, status, statusText);
                            throw new HttpClientErrorException(status, statusText);
                        })).toBodilessEntity();
        log.debug("the request was successfully {}{} offers executed.", dealUri, endpoint);
    }

    @Override
    public void calculate(String statementId, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        String endpoint = "calculate?statementId=" + statementId;

        log.trace("the beginning of sending a request to {}{} MS deal", dealUri, endpoint);
        restClient.post()
                .uri(dealUri + endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(finishRegistrationRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        ((request, response) -> {
                            HttpStatusCode status = response.getStatusCode();
                            String statusText = response.getStatusText();
                            log.error("request sending error {}{}: Error {} -> {}", dealUri, endpoint, status, statusText);
                            throw new HttpClientErrorException(status, statusText);
                        }));
        log.debug("the request was successfully {}{} offers executed.", dealUri, endpoint);
    }
}
