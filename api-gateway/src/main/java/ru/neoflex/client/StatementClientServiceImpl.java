package ru.neoflex.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;

import java.util.List;

@Service
@Slf4j
public class StatementClientServiceImpl implements StatementClientService{
    private final RestClient restClient = RestClient.create();
    private final String uri = "http://localhost:8083/statement/";

    @Override
    public List<LoanOfferDto> calcLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        log.trace("the beginning of sending a request to {} MS statement", uri);

        List<LoanOfferDto> loanOffers = restClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(loanStatementRequestDto)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        log.debug("the request was successfully {} offers executed. Result: {}: {} size", uri, loanOffers.getClass(), loanOffers.size());
        return loanOffers;
    }

    @Override
    public void offer(LoanOfferDto loanOfferDto) {
        String endpoint = "offer";

        log.trace("the beginning of sending a request to {}{} MS deal", uri, endpoint);
        restClient.post()
                .uri(uri + endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(loanOfferDto)
                .retrieve()
                .toBodilessEntity();
        log.debug("the request was successfully {}{} offers executed.", uri, endpoint);
    }
}
