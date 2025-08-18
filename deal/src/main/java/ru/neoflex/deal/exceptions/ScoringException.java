package ru.neoflex.deal.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

public class ScoringException extends HttpClientErrorException {
    public ScoringException(HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);
    }
}
