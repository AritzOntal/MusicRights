package com.svalero.music.rights.service;

import com.svalero.music.rights.domain.Claim;
import com.svalero.music.rights.domain.Document;
import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.exception.ClaimNotFoundException;
import com.svalero.music.rights.exception.DocumentNotFoundException;
import com.svalero.music.rights.exception.MusicianNotFoundException;
import com.svalero.music.rights.repository.ClaimRepository;
import com.svalero.music.rights.repository.DocumentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final ClaimRepository claimRepository;
    private final ClaimService claimService;

    public DocumentService(DocumentRepository documentRepository, ClaimRepository claimRepository, ClaimService claimService) {
        this.documentRepository = documentRepository;
        this.claimRepository = claimRepository;
        this.claimService = claimService;
    }

        public Document add(Document document) {
            Long idClaim = document.getClaim().getId();

            if (idClaim != null) {
                Claim claim = claimRepository.findById(idClaim)
                        .orElseThrow(ClaimNotFoundException::new);
                document.setClaim(claim);
            }
            return documentRepository.save(document);
        }

    public ResponseEntity<List<Document>> findAll(String type, Boolean complete, LocalDate createAd) {

        List<Document> documents;

        if ((type != null && !type.isBlank()) & (createAd != null) & (complete != null)) {
            documents = documentRepository.findByTypeAndCompleteAndCreateAt(type, complete, createAd);
            return ResponseEntity.ok().body(documents);

        } else {
            documents = documentRepository.findAll();
            return ResponseEntity.ok().body(documents);
        }
    }

    public Document findById(Long id) {
        return documentRepository.findById(id).orElseThrow(() -> new RuntimeException("document_not_found"));
    }

    public Document findByClaim(Long id) throws ClaimNotFoundException {
        Claim claim = claimService.findById(id);
        if (claim == null) {
            throw (new ClaimNotFoundException());
        }
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
