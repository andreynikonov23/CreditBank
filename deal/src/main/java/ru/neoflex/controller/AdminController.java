package ru.neoflex.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.dao.DAO;
import ru.neoflex.model.Statement;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/deal/admin/statement")
@Slf4j
public class AdminController {
    private final DAO<Statement, UUID> statementDAO;

    public AdminController(DAO<Statement, UUID> statementDAO) {
        this.statementDAO = statementDAO;
    }

    @GetMapping
    public List<Statement> getAllStatements() {
        log.info("/deal/admin/statement");
        return statementDAO.getAll();
    }

    @GetMapping("/{statementId}")
    public Statement getStatement(@PathVariable("statementId") String statementId) {
        log.info("/deal/admin/statement/{}", statementId);
        return statementDAO.findById(UUID.fromString(statementId));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        String errorMessage = "no data was found in the database: " + ex.getMessage();
        log.debug(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
