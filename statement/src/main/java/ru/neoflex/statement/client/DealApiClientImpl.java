package ru.neoflex.statement.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.neoflex.statement.dto.LoanOfferDto;
import ru.neoflex.statement.dto.LoanStatementRequestDto;

import java.util.List;

@Service
@Slf4j
public class DealApiClientImpl implements DealApiClient{
    private final RestClient restClient;
    @Value("${creditbank.deal.host}")
    private String dealUri;

    public DealApiClientImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<LoanOfferDto> statement(LoanStatementRequestDto loanStatementRequestDto) {
        String endpoint = "/statement";
        log.trace("the beginning of sending a request to {}{} MS deal", dealUri, endpoint);

        List<LoanOfferDto> loanOffers = restClient.post()
                .uri(dealUri + endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(loanStatementRequestDto)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        log.debug("the request was successfully {}{} offers executed. Result: {}: {} size", dealUri, endpoint, loanOffers.getClass(), loanOffers.size());
        return loanOffers;
    }

    @Override
    public void select(LoanOfferDto loanOfferDto) {
        String endpoint = "/select";

        log.trace("the beginning of sending a request to {}{} MS deal", dealUri, endpoint);
        restClient.post()
                .uri(dealUri + endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(loanOfferDto)
                .retrieve()
                .toBodilessEntity();
        log.debug("the request was successfully {}{} offers executed.", dealUri, endpoint);
    }
}
