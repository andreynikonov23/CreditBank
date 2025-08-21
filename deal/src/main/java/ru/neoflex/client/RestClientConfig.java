package ru.neoflex.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.neoflex.exceptions.ScoringException;

import java.util.NoSuchElementException;

@Configuration
public class RestClientConfig {
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    HttpStatusCode statusCode = response.getStatusCode();
                    String statusText = response.getStatusText();

                    switch (statusCode.value()) {
                        case 422 -> throw new ScoringException(statusText);
                        case 404 -> throw new NoSuchElementException(statusText);
                        default -> throw new HttpClientErrorException(statusCode, statusText);
                    }
                }).build();
    }
}
