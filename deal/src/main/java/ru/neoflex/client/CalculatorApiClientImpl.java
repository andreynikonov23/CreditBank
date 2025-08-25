package ru.neoflex.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.neoflex.exceptions.ScoringException;
import ru.neoflex.dto.CreditDto;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;
import ru.neoflex.dto.ScoringDataDto;

import java.util.List;

@Service
@Slf4j
public class CalculatorApiClientImpl implements CalculatorApiClient{
    private final RestClient restClient = RestClient.create();
    @Value("${creditbank.calculator.host}")
    private String calculatorUri;

    @Override
    public List<LoanOfferDto> offers(LoanStatementRequestDto loanStatementRequestDto) {
        String endpoint = "/offers";
        log.trace("the beginning of sending a request to {}{} MS calculator", calculatorUri, endpoint);

        List<LoanOfferDto> loanOffers = restClient.post()
                .uri(calculatorUri + endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(loanStatementRequestDto)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        log.debug("the request was successfully {}{} offers executed. Result: {}: {} size", calculatorUri, endpoint, loanOffers.getClass(), loanOffers.size());
        return loanOffers;
    }

    @Override
    public CreditDto calc(ScoringDataDto scoringDataDto) {
        String endpoint = "/calc";
        log.trace("the beginning of sending a request to {}{} MS calculator", calculatorUri, endpoint);

        CreditDto creditDto = restClient.post()
                .uri(calculatorUri + endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(scoringDataDto)
                .retrieve()
                .body(CreditDto.class);

        log.debug("the request was successfully {}{} offers executed. Result: {}", calculatorUri, endpoint, creditDto);
        return creditDto;
    }
}
