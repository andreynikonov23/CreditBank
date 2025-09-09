package ru.neoflex.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.client.HttpClientErrorException;
import ru.neoflex.dto.FinishRegistrationRequestDto;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;
import ru.neoflex.service.DealService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/deal")
public class DealController {
    private final DealService dealService;

    public DealController(DealService dealServiceImpl) {
        this.dealService = dealServiceImpl;
    }

    @Operation(summary = "Prescoring and calculate loan offers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LoanOfferDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(mediaType = "application/json", examples = @ExampleObject("[invalid email, invalid passport series, invalid passport series, age must be over 18 years old., lastname is empty]"))),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content(mediaType = "application/json", examples = @ExampleObject("Unfortunately, the loan calculation service is currently unavailable")))
    })
    @PostMapping("/statement")
    public List<LoanOfferDto> calcLoanTerms(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto){
        log.info("/deal/statement with body {}", loanStatementRequestDto);
        return dealService.calcLoanTerms(loanStatementRequestDto);
    }

    @Operation(summary = "Choose a loan offer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(mediaType = "application/json", examples = @ExampleObject("[statementId is null, the amount must be greater than or equal to 20000]"))),
            @ApiResponse(responseCode = "404", description = "Error accessing the resource", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement data with uuid = ... not found"))),
            @ApiResponse(responseCode = "409", description = "Statement status conflict", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement has denied")))
    })
    @PostMapping("/select")
    public void selectLoanOffer(
            @Parameter(description = "Specify the parameter if the client refuses the generated offers.")
            @RequestParam(value = "denied-statement", required = false)String statementId,
            @Valid @RequestBody(required = false) LoanOfferDto loanOfferDto) {
        log.info("/deal/select/ with param {} and body {}", statementId, loanOfferDto);
        if (statementId != null) {
            dealService.clientDenied(statementId);
        } else if (loanOfferDto == null) {
            String errorMessage = "loan offer is null";
            log.error("/deal/select/ error: {}", errorMessage);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }else
            dealService.selectLoanOffer(loanOfferDto);
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
    @PostMapping("/calculate")
    public void calculate(@RequestParam("statementId") String statementId, @Valid @RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto) {
        log.info("/deal/calculate/{} with body {}", statementId, finishRegistrationRequestDto);
        dealService.calculate(statementId, finishRegistrationRequestDto);
    }
}
