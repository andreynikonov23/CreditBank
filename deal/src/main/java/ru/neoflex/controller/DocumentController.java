package ru.neoflex.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.service.DealService;
import ru.neoflex.service.DocumentService;

@RestController
@Slf4j
@RequestMapping("/deal/document")
public class DocumentController {
    private final DocumentService documentService;
    private final DealService dealService;

    public DocumentController(DocumentService documentService, DealService dealService) {
        this.documentService = documentService;
        this.dealService = dealService;
    }

    @Operation(summary = "Request the formation of documents")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "Error accessing the resource", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement data with uuid = ... not found"))),
            @ApiResponse(responseCode = "409", description = "Statement status conflict", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement has denied")))
    })
    @PostMapping("/{statementId}/send")
    public void requestSendDocuments(@PathVariable("statementId") String statementId) {
        log.info("/deal/{}/send", statementId);
        documentService.sendDocument(statementId);
    }

    @Operation(summary = "Request to sign documents")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "Error accessing the resource", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement data with uuid = ... not found"))),
            @ApiResponse(responseCode = "409", description = "Statement status conflict", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement has denied")))
    })
    @PostMapping("/{statementId}/sign")
    public void requestSignDocuments(@PathVariable("statementId") String statementId) {
        log.info("/deal/{}/sign", statementId);
        documentService.registerSigning(statementId);
    }

    @Operation(summary = "Refusal to sign documents")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "Error accessing the resource", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement data with uuid = ... not found"))),
            @ApiResponse(responseCode = "409", description = "Statement status conflict", content = @Content(mediaType = "application/json", examples = @ExampleObject("the credit has already been issued for the statement 23df2-3jfdsf3-fjdkss"))),
    })
    @PostMapping("/{statementId}/denied")
    public void clientDenied(@PathVariable("statementId") String statementId) {
        log.info("/deal/{}/denied", statementId);
        dealService.clientDenied(statementId);
    }

    @Operation(summary = "Sign document")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "Error accessing the resource", content = @Content(mediaType = "application/json", examples = @ExampleObject("statement data with uuid = ... not found"))),
            @ApiResponse(responseCode = "409", description = "Statement status conflict", content = @Content(mediaType = "application/json", examples = @ExampleObject("not matching ses_codes"))),
    })
    @PostMapping("/{statementId}/{code}")
    public void signDocuments(@PathVariable("statementId") String statementId,
                              @Parameter(description = "generated ses-code for user identification")
                              @PathVariable("code") String sesCode) {
        log.info("/deal/{}/{}", statementId, sesCode);
        documentService.signDocument(statementId, sesCode);
    }
}
