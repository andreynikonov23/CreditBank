package ru.neoflex.statement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import ru.neoflex.statement.exceptions.MicroserviceException;
import ru.neoflex.statement.exceptions.StatementStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
        log.debug("valid error: " + errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MicroserviceException.class)
    public ResponseEntity<String> handleServiceExceptions(MicroserviceException ex) {
        log.debug("MS " + ex.getMicroserviceName() + " is not available: " + ex.getMessage());
        return new ResponseEntity<>("Unfortunately, this feature is currently unavailable.",
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        String errorMessage = "no data was found in the database: " + ex.getMessage();
        log.debug(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientExceptions(HttpClientErrorException ex) {
        String errorMessage = "HTTP error: " + ex.getMessage();
        log.debug(errorMessage);
        return new ResponseEntity<>(errorMessage, ex.getStatusCode());
    }

    @ExceptionHandler(StatementStatusException.class)
    public ResponseEntity<String> handleStatementStatusException(StatementStatusException ex) {
        String errorMessage = "the statement status exception: " + ex.getMessage();
        log.debug(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
}
