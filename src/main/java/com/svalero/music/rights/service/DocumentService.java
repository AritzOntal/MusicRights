package com.svalero.music.rights.service;

import com.svalero.music.rights.domain.Claim;
import com.svalero.music.rights.domain.Document;
import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void add(Document document) {
        documentRepository.save(document);
    }

    public List<Document> findAll() {
        List<Document> documents = documentRepository.findAll();
        return documents;
    }

    public List<Document> findAllByClaim(Claim claim) {
        List <Document> documents = documentRepository.findAllByClaim(claim);
        return documents;
    }
    //LISTA DE DOCUMENTOS ASOCIADOS A UN CLAIM

    public void delete(Document document) {
        documentRepository.delete(document);
    }

    public Document findById(Long id){
       Document document = documentRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("document_not_found"));
       return document;
    }


}
