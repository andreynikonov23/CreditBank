package ru.neoflex.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.neoflex.client.DealClientService;
import ru.neoflex.client.StatementClientService;
import ru.neoflex.dto.FinishRegistrationRequestDto;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;

import java.util.List;

@RestController
@RequestMapping("/api/gateway")
@Slf4j
public class ApiController {
    private final StatementClientService statementClientService;
    private final DealClientService dealClientService;

    public ApiController(StatementClientService statementClientService, DealClientService dealClientService) {
        this.statementClientService = statementClientService;
        this.dealClientService = dealClientService;
    }

    @Operation(summary = "Prescoring and calculate loan offers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LoanOfferDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(mediaType = "application/json", examples = @ExampleObject("[invalid email, invalid passport series, invalid passport series, age must be over 18 years old., lastname is empty]"))),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content(mediaType = "application/json", examples = @ExampleObject("Unfortunately, the loan calculation service is currently unavailable")))
    })
    @PostMapping("/statement")
    public List<LoanOfferDto> calcLoanOffers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info("/api/gateway/statement with body {}", loanStatementRequestDto);
        return statementClientService.calcLoanOffers(loanStatementRequestDto);
    }

    @Operation(summary = "Choose a loan offer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(mediaType = "application/json", examples = @ExampleObject("[statementId is null, the amount must be greater than or equal to 20000]"))),
            @ApiResponse(responseCode = "404", description = "Error accessing the resource", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement data with uuid = ... not found"))),
            @ApiResponse(responseCode = "409", description = "Statement status conflict", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement has denied")))
    })
    @PostMapping("/statement/offer")
    public void selectLoanOffer(@RequestParam(value = "denied-statement", required = false)String statementId, @Valid @RequestBody(required = false) LoanOfferDto loanOfferDto) {
        log.info("/api/gateway/statement/offer with param {} and body {}", statementId, loanOfferDto);
        if (statementId != null) {
            statementClientService.clientDenied(statementId);
        } else if (loanOfferDto == null) {
            String errorMessage = "loan offer is null";
            log.error("/api/gateway/statement/offer error: {}", errorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        } else
            statementClientService.offer(loanOfferDto);
    }

    @Operation(summary = "Scoring and credit calculation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(mediaType = "application/json", examples = @ExampleObject("[invalid passport issue date, passportIssueBranch is empty"))),
            @ApiResponse(responseCode = "404", description = "Error accessing the resource", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement data with uuid = ... not found"))),
            @ApiResponse(responseCode = "409", description = "Statement status conflict", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement has denied"))),
            @ApiResponse(responseCode = "422", description = "Failed scoring", content = @Content(mediaType = "application/json", examples = @ExampleObject("the client is unemployed"))),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content(mediaType = "application/json", examples = @ExampleObject("Unfortunately, the loan calculation service is currently unavailable")))
    })
    @PostMapping("/deal/calculate")
    public void calculateCredit(@RequestParam("statementId") String statementId, @RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto) {
        log.info("/api/gateway/deal/calculate/{} with body {}", statementId, finishRegistrationRequestDto);
        dealClientService.calculate(statementId, finishRegistrationRequestDto);
    }

    @Operation(summary = "Request the formation of documents")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "Error accessing the resource", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement data with uuid = ... not found"))),
            @ApiResponse(responseCode = "409", description = "Statement status conflict", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement has denied")))
    })
    @PostMapping("/deal/document/{statementId}/send")
    public void requestSendDocuments(@PathVariable("statementId") String statementId) {
        log.info("/api/gateway/deal/document/{}/send", statementId);
        dealClientService.requestSendDocuments(statementId);
    }

    @Operation(summary = "Request to sign documents")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "Error accessing the resource", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement data with uuid = ... not found"))),
            @ApiResponse(responseCode = "409", description = "Statement status conflict", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement has denied")))
    })
    @PostMapping("/deal/document/{statementId}/sign")
    public void requestSignDocuments(@PathVariable("statementId") String statementId) {
        log.info("/api/gateway/deal/document/{}/sign", statementId);
        dealClientService.requestSignDocuments(statementId);
    }

    @Operation(summary = "Sign document")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "Error accessing the resource", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement data with uuid = ... not found"))),
            @ApiResponse(responseCode = "409", description = "Statement status conflict", content = @Content(mediaType = "application/json", examples = @ExampleObject("not matching ses_codes"))),
    })
    @PostMapping("/deal/document/{statementId}/{code}")
    public void signDocument(@PathVariable("statementId") String statementId, @PathVariable("code") String sesCode) {
        log.info("/api/gateway/deal/document/{}/{}", statementId, sesCode);
        dealClientService.signDocument(statementId, sesCode);
    }

    @Operation(summary = "Refusal to sign documents")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "Error accessing the resource", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement data with uuid = ... not found"))),
            @ApiResponse(responseCode = "409", description = "Statement status conflict", content = @Content(mediaType = "application/json", examples = @ExampleObject("the credit has already been issued for the statement 23df2-3jfdsf3-fjdkss"))),
    })
    @PostMapping("/deal/document/{statementId}/denied")
    public void clientDenied(@PathVariable("statementId") String statementId) {
        log.info("/api/gateway/deal/document/{}/denied", statementId);
        dealClientService.clientDenied(statementId);
    }
}
