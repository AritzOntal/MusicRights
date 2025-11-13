package com.svalero.music.rights.controller;

import com.svalero.music.rights.domain.Claim;
import com.svalero.music.rights.domain.Concert;
import com.svalero.music.rights.domain.Document;
import com.svalero.music.rights.exception.ClaimNotFoundException;
import com.svalero.music.rights.exception.ConcertNotFoundException;
import com.svalero.music.rights.exception.DocumentNotFoundException;
import com.svalero.music.rights.exception.ErrorResponse;
import com.svalero.music.rights.repository.DocumentRepository;
import com.svalero.music.rights.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/documents")
    public ResponseEntity <List<Document>> getAll(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "complete", required = false) Boolean complete,
            @RequestParam(value = "createAd", required = false) LocalDate createAd
            ) {
        List<Document> concerts;

        if ((type != null && !type.isBlank()) & (createAd != null) & (complete != null)) {
            concerts = documentService.findByParameters(type, complete, createAd);

        } else {

            concerts = documentService.findAll();
        }
            return ResponseEntity.ok().body(concerts);
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity <Document> get(@PathVariable Long id) {
        Document document = documentService.findById(id);
        return ResponseEntity.ok().body(document);
    }

    @PostMapping("/documents")
    public ResponseEntity <Document> create (@RequestBody Document document) {
        documentService.add(document);
        return  ResponseEntity.ok().body(document);
    }

    @PutMapping("/documents/{id}")
    public ResponseEntity <Document> update (@RequestBody Document document, @PathVariable Long id) {
        documentService.edit(id,  document);
        return  ResponseEntity.ok().body(document);
    }

    @DeleteMapping("/documents/{id}")
    public ResponseEntity <Void> delete(@PathVariable Long id) {
        documentService.delete(id);
        return  ResponseEntity.noContent().build();
    }

    //FILTRADOS
    @GetMapping("/documents/by-claim/{id}")
    public ResponseEntity <Document> getByClaim(@PathVariable Long id) throws ClaimNotFoundException {
        Document documentOfClaim = documentService.findByClaim(id);
        return ResponseEntity.ok().body(documentOfClaim);
    }

}
