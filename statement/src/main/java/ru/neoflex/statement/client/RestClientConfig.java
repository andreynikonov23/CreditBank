package ru.neoflex.statement.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.neoflex.statement.exceptions.MicroserviceException;
import ru.neoflex.statement.exceptions.MicroserviceName;
import ru.neoflex.statement.exceptions.StatementStatusException;

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
                        case 404 -> throw new NoSuchElementException(statusText);
                        case 409 -> throw new StatementStatusException(statusText);
                        default -> throw new MicroserviceException(MicroserviceName.DEAL, statusCode, statusText);
                    }
                }).build();
    }
}
