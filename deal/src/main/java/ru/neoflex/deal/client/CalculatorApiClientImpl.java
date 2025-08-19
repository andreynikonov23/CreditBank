package ru.neoflex.deal.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.neoflex.deal.exceptions.ScoringException;
import ru.neoflex.deal.model.dto.CreditDto;
import ru.neoflex.deal.model.dto.LoanOfferDto;
import ru.neoflex.deal.model.dto.LoanStatementRequestDto;
import ru.neoflex.deal.model.dto.ScoringDataDto;

import java.util.List;

@Service
@Slf4j
public class CalculatorApiClientImpl implements CalculatorApiClient{
    private final RestClient restClient = RestClient.create();
    private final String calculatorUri = "http://localhost:8081/calculator/";

    @Override
    public List<LoanOfferDto> offers(LoanStatementRequestDto loanStatementRequestDto) {
        String endpoint = "offers";
        log.trace("the beginning of sending a request to {}{} MS calculator", calculatorUri, endpoint);

        List<LoanOfferDto> loanOffers = restClient.post()
                .uri(calculatorUri + endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(loanStatementRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        ((request, response) -> {
                            HttpStatusCode status = response.getStatusCode();
                            String statusText = response.getStatusText();
                            log.error("request sending error {}{}: Error {} -> {}", calculatorUri, endpoint, status, statusText);
                            throw new HttpClientErrorException(status, statusText);
                        }))
                .body(new ParameterizedTypeReference<>() {});

        log.debug("the request was successfully {}{} offers executed. Result: {}: {} size", calculatorUri, endpoint, loanOffers.getClass(), loanOffers.size());
        return loanOffers;
    }

    @Override
    public CreditDto calc(ScoringDataDto scoringDataDto) {
        String endpoint = "calc";
        log.trace("the beginning of sending a request to {}{} MS calculator", calculatorUri, endpoint);

        CreditDto creditDto = restClient.post()
                .uri(calculatorUri + endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(scoringDataDto)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        ((request, response) -> {
                            HttpStatusCode status = response.getStatusCode();
                            String statusText = response.getStatusText();
                            log.error("request sending error {}{}: Error {} -> {}", calculatorUri, endpoint, status, statusText);
                            if (status.value() == 422) {
                                throw new ScoringException(statusText);
                            } else
                                throw new HttpClientErrorException(status, statusText);
                        }))
                .body(CreditDto.class);

        log.debug("the request was successfully {}{} offers executed. Result: {}", calculatorUri, endpoint, creditDto);
        return creditDto;
    }
}
