package ru.neoflex.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.client.DealClientService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/gateway/deal/admin/statement")
@Slf4j
public class AdminController {
    private final DealClientService dealClientService;

    public AdminController(DealClientService dealClientService) {
        this.dealClientService = dealClientService;
    }

    @GetMapping
    public String getAllStatements() {
        log.info("/api/gateway/deal/admin/statement");
        return dealClientService.getAllStatements();
    }

    @GetMapping("/{statementId}")
    public String getStatement(@PathVariable("statementId") String statementId) {
        log.info("/api/gateway/deal/admin/statement/{}", statementId);
        return dealClientService.findStatementById(statementId);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        String errorMessage = "no data was found in the database: " + ex.getMessage();
        log.debug(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
