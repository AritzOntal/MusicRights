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

    public Document findById(Long id) {
        return documentRepository.findById(id).orElseThrow(() -> new RuntimeException("document_not_found"));
    }

    public Document findByClaim(Long id) {
        return documentRepository.findByClaimId(id);
    }
    //LISTA DE DOCUMENTOS ASOCIADOS A UN CLAIM

    public Document edit(Long id, Document updateDocument) {

        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("document_not_found"));

        document.setComplete(updateDocument.isComplete());
        document.setType(updateDocument.getType());
        document.setFilename(updateDocument.getFilename());
        document.setSize(updateDocument.getSize());
        document.setCreateAt(updateDocument.getCreateAt());

        documentRepository.save(document);

        return document;
    }

    public void delete(Long id) {
        documentRepository.deleteById(id);
    }
}
