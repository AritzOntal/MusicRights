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

import java.util.List;

@RestController
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/documents")
    public List<Document> getAll() {
        List<Document> documents = documentService.findAll();
        return documents;
    }

    @GetMapping("/documents/{id}")
    public Document get(@PathVariable Long id) {
        Document document = documentService.findById(id);
        return document;
    }

    @PostMapping("/documents")
    public void create (@RequestBody Document document) {
        documentService.add(document);
    }

    @PutMapping("/documents/{id}")
    public void update (@RequestBody Document document, @PathVariable Long id) {
        documentService.edit(id,  document);
    }

    @DeleteMapping("/documents/{id}")
    public void delete(@PathVariable Long id) {
        documentService.delete(id);
    }

    //FILTRADOS
    @GetMapping("/documents/by-claim/{id}")
    public Document getByClaim(@PathVariable Long id) throws ClaimNotFoundException {
        Document documentOfClaim = documentService.findByClaim(id);
        return documentOfClaim;
    }

}
