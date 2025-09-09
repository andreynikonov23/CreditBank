package ru.neoflex.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all statements")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class, type = "array"))))
    })
    @GetMapping
    public String getAllStatements() {
        log.info("/api/gateway/deal/admin/statement");
        return dealClientService.getAllStatements();
    }

    @Operation(summary = "Request the formation of documents")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Error accessing the resource", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement data with uuid = ... not found")))
    })
    @GetMapping("/{statementId}")
    public String getStatement(@PathVariable("statementId") String statementId) {
        log.info("/api/gateway/deal/admin/statement/{}", statementId);
        return dealClientService.findStatementById(statementId);
    }
}
