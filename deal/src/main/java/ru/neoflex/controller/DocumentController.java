package ru.neoflex.controller;

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

    @PostMapping("/{statementId}/send")
    public void requestSendDocuments(@PathVariable("statementId") String statementId) {
        log.info("/deal/{}/send", statementId);
        documentService.sendDocument(statementId);
    }

    @PostMapping("/{statementId}/sign")
    public void requestSignDocuments(@PathVariable("statementId") String statementId) {
        log.info("/deal/{}/sign", statementId);
        documentService.registerSigning(statementId);
    }

    @PostMapping("/{statementId}/denied")
    public void clientDenied(@PathVariable("statementId") String statementId) {
        log.info("/deal/{}/denied", statementId);
        dealService.clientDenied(statementId);
    }

    @PostMapping("/{statementId}/{code}")
    public void signDocuments(@PathVariable("statementId") String statementId, @PathVariable("code") String sesCode) {
        log.info("/deal/{}/{}", statementId, sesCode);
        documentService.signDocument(statementId, sesCode);
    }
}
