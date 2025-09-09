package ru.neoflex.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.dao.DAO;
import ru.neoflex.model.Statement;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deal/admin/statement")
@Slf4j
public class AdminController {
    private final DAO<Statement, UUID> statementDAO;

    public AdminController(DAO<Statement, UUID> statementDAO) {
        this.statementDAO = statementDAO;
    }

    @Operation(summary = "Get all statements")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Statement.class))))
    })
    @GetMapping
    public List<Statement> getAllStatements() {
        log.info("/deal/admin/statement");
        return statementDAO.getAll();
    }

    @Operation(summary = "Request the formation of documents")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Statement.class))),
            @ApiResponse(responseCode = "404", description = "Error accessing the resource", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement data with uuid = ... not found")))
    })
    @GetMapping("/{statementId}")
    public Statement getStatement(@PathVariable("statementId") String statementId) {
        log.info("/deal/admin/statement/{}", statementId);
        return statementDAO.findById(UUID.fromString(statementId));
    }
}
