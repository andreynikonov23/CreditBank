package ru.neoflex.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import ru.neoflex.exceptions.ScoringException;
import ru.neoflex.exceptions.SignDocumentException;
import ru.neoflex.exceptions.StatementStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
        log.debug("valid error: " + errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientExceptions(HttpClientErrorException ex) {
        log.debug("Service is not available: " + ex.getMessage());
        return new ResponseEntity<>("Unfortunately, this feature is currently unavailable.",
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ScoringException.class)
    public ResponseEntity<String> handleScoringExceptions(ScoringException ex) {
        String errorMessage = "the request failed scoring: " + ex.getMessage();
        log.debug(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        String errorMessage = "no data was found in the database: " + ex.getMessage();
        log.debug(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleSignDocumentException(SignDocumentException ex) {
        String errorMessage = "the request sign document failed: " + ex.getMessage();
        log.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(StatementStatusException.class)
    public ResponseEntity<String> handleStatementStatusException(StatementStatusException ex) {
        String errorMessage = "the statement status exception: " + ex.getMessage();
        log.debug(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleHttpClientExceptions(ResponseStatusException ex) {
        String errorMessage = "HTTP error: " + ex.getMessage();
        log.debug(errorMessage);
        return new ResponseEntity<>(errorMessage, ex.getStatusCode());
    }
}
