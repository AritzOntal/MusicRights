package com.svalero.music.rights.service;

import com.svalero.music.rights.domain.*;
import com.svalero.music.rights.exception.*;
import com.svalero.music.rights.repository.*;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

    @InjectMocks
    private DocumentService documentService;
    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private ClaimRepository claimRepository;


    //FIND ALL

    @Test
    void findAllOK() {

        Claim claim = new Claim(1L, "REF-001", "OPEN", "COPYRIGHT", "Uso no autorizado de obra", true, null);

        List<Document> documents = List.of(
                new Document(1L, "PDF", "contrato_2026.pdf", 524000L, LocalDate.of(2026, 3, 15), true, 100.0f, claim),
                new Document(2L, "IMG", "foto_reclamacion.png", 128000L, LocalDate.of(2026, 1, 10), false, 45.5f, claim)
        );

        when(documentRepository.findAll()).thenReturn(documents);

        ResponseEntity<List<Document>> result = documentService.findAll(null, null, null);

        List<Document> documentList = result.getBody();
        assertEquals(2, documentList.size());

    }

    //ADD

    @Test
    void testAddOk() {

        Claim claim = new Claim(1L, "REF-001", "OPEN", "COPYRIGHT", "Uso no autorizado de obra", true, null);
        Document document = new Document(1L, "PDF", "contrato_2026.pdf", 524000L, LocalDate.of(2026, 3, 15), true, 100.0f, claim);

        when(claimRepository.findById(claim.getId()))
                .thenReturn(Optional.of(claim));

        when(documentRepository.save(any(Document.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Document result = documentService.add(document);
        assertSame(document, result);

        verify(documentRepository, times(1)).save(document);
    }


    @Test
    void testAddDocumentNotFound() {
        Long noExistingId = 1L;
        Document document = new Document();
        Claim claimMock = new Claim();
        claimMock.setId(noExistingId);
        document.setClaim(claimMock);

        when(claimRepository.findById(noExistingId)).thenReturn(Optional.empty());

        assertThrows(ClaimNotFoundException.class,
                () -> documentService.add(document));
    }

    //FIND BY ID

    @Test
    void testFindByIdOk() {

        Long existingId = 1L;
        Document document = new Document();
        document.setId(existingId);

        when(documentRepository.findById(existingId))
                .thenReturn(Optional.of(document));

        Document result = documentService.findById(existingId);
        assertSame(document, result);
    }

    @Test
    void testFindByIdException() {
        Long notExistingId = 99L;

        when(documentRepository.findById(notExistingId))
                .thenReturn(Optional.empty());

        assertThrows(DocumentNotFoundException.class,
                () -> documentService.findById(notExistingId));
    }

    //EDIT

    @Test
    void testEditOK() {
        Long idDoc = 1L;
        Document documentDb = new Document();
        documentDb.setId(idDoc);

        Document newDocument = new Document();
        newDocument.setFilename("Hola");

        when(documentRepository.findById(idDoc))
                .thenReturn(Optional.of(documentDb));

        when(documentRepository.save(any(Document.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Document result = documentService.edit(idDoc, newDocument);
        assertSame(documentDb, result);
    }

    @Test
    void testModifyNotFound() {
        Long noExist = 1L;
        Document documentDb = new Document();
        documentDb.setId(noExist);

        Document newDocument = new Document();
        newDocument.setComplete(false);

        when(documentRepository.findById(noExist))
                .thenReturn(Optional.empty());

        assertThrows(DocumentNotFoundException.class,
                () -> documentService.edit(noExist, newDocument));
    }


    //DELETE

    @Test
    void testDeleteOk() {
        Long existId = 1L;

        when(documentRepository.findById(existId))
                .thenReturn(Optional.of(new Document()));

        documentService.delete(existId);

        verify(documentRepository, times(1)).deleteById(existId);
    }

    @Test
    void testDeleteNotFound() {
        Long noExistingId = 1L;

        when(documentRepository.findById(noExistingId))
                .thenReturn(Optional.empty());

        assertThrows(DocumentNotFoundException.class,
                () -> documentService.delete(noExistingId));
    }
}


