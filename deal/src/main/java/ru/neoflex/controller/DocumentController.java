package ru.neoflex.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.exceptions.SignDocumentException;
import ru.neoflex.service.DocumentService;
import ru.neoflex.service.DocumentServiceImpl;

@RestController
@Slf4j
@RequestMapping("/deal/document")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
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

    @PostMapping("/{statementId}/{code}")
    public void signDocuments(@PathVariable("statementId") String statementId, @PathVariable("code") String sesCode) {
        log.info("/deal/{}/{}", statementId, sesCode);
        documentService.signDocument(statementId, sesCode);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleSignDocumentException(SignDocumentException ex) {
        String errorMessage = "the request sign document failed: " + ex.getMessage();
        log.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }
}
