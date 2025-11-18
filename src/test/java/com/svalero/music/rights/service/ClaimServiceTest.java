package com.svalero.music.rights.service;

import com.svalero.music.rights.domain.Claim;
import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.domain.Work;
import com.svalero.music.rights.exception.ClaimNotFoundException;
import com.svalero.music.rights.exception.MusicianNotFoundException;
import com.svalero.music.rights.exception.WorkNotFoundException;
import com.svalero.music.rights.repository.ClaimRepository;
import com.svalero.music.rights.repository.MusicianRepository;
import com.svalero.music.rights.repository.WorkRepository;
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
public class ClaimServiceTest {

    @InjectMocks // Crea instancia real he inyecta los mocks
    private ClaimService claimService;
    @Mock //esto es un mock para lo que yo configure con "when..."
    private ClaimRepository claimRepository;
    @Mock
    private MusicianRepository musicianRepository;


    //FIND ALL

    @Test
    void findAllOK() {

        Musician musician = new Musician(10L, "Juan", "Pérez", null, false, null, 0f, 0L, List.of(), List.of());
        List<Claim> claims = List.of(
                new Claim(1L, "REF-001", "OPEN", "COPYRIGHT", "Reclamación por uso no autorizado de obra.", true, musician),
                new Claim(2L, "REF-002", "IN_PROGRESS", "ROYALTIES", "Reclamación de impagos de derechos.", true, musician)
        );

        when(claimRepository.findAll()).thenReturn(claims);

        ResponseEntity<List<Claim>> claimsMocked = claimService.findAll(null, null, null);
        List<Claim> result = claimsMocked.getBody();
        assertEquals(2, result.size());

    }

    //ADD

    @Test
    void testAddOk() {

        Musician musician = new Musician(10L, "Juan", "Pérez", null, false, null, 0f, 0L, List.of(), List.of());
        Claim claim = new Claim(1L, "REF-001", "OPEN", "COPYRIGHT", "Reclamación por uso no autorizado de obra.", true, musician);

        when(musicianRepository.findById(musician.getId()))
                .thenReturn(Optional.of(musician));

        when(claimRepository.save(any(Claim.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Claim result = claimService.add(claim);
        assertSame(claim, result);

        verify(musicianRepository, times(1)).findById(musician.getId());
        verify(claimRepository, times(1)).save(claim);
    }


    @Test
    void testAddClaimNotFound() {
        Long noExistingId = 1L;
        Claim claim = new Claim();
        Musician musicianMock = new Musician();
        musicianMock.setId(noExistingId);
        claim.setMusician(musicianMock);

        when(musicianRepository.findById(noExistingId))
                .thenReturn(Optional.empty());

        assertThrows(MusicianNotFoundException.class,
                () -> claimService.add(claim));
    }

    //FIND BY ID

    @Test
    void testFindByIdOk() {

        Long existingId = 1L;
        Claim claim = new Claim();
        claim.setId(existingId);

        when(claimRepository.findById(existingId))
                .thenReturn(Optional.of(claim));

        Claim result = claimService.findById(existingId);
        assertSame(claim, result);
    }

    @Test
    void testFindByIdException() {
        Long notExistingId = 99L;

        when(claimRepository.findById(notExistingId))
                .thenReturn(Optional.empty());

        assertThrows(ClaimNotFoundException.class,
                () -> claimService.findById(notExistingId));
    }

    //EDIT

    @Test
    void testEditOK() {
        Long existingId = 1L;
        Claim claimDb = new Claim();
        claimDb.setId(existingId);

        Claim newClaim = new Claim();
        newClaim.setReference("1231241");

        when(claimRepository.findById(existingId))
                .thenReturn(Optional.of(claimDb));

        when(claimRepository.save(any(Claim.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        //La lllamada de verdad
        Claim result = claimService.modify(existingId, newClaim);

        assertSame(claimDb, result);
    }

    @Test
    void testModifyNotFound() {
        Long noExist = 1L;
        Claim claimDb = new Claim();
        claimDb.setId(noExist);

        Claim newClaim = new Claim();
        newClaim.setReference("12243221");

        when(claimRepository.findById(noExist))
                .thenReturn(Optional.empty());

        assertThrows(ClaimNotFoundException.class,
                () -> claimService.modify(noExist, newClaim));
    }


    //DELETE

        @Test
        void testDeleteOk() {
            Long existId = 1L;

            when(claimRepository.findById(existId))
                    .thenReturn(Optional.of(new Claim()));

            claimService.delete(existId);
            verify(claimRepository, times(1)).findById(existId);
        }

    @Test
    void testDeleteNotFound() {
        Long noExistingId = 1L;

        when(claimRepository.findById(noExistingId))
                .thenReturn(Optional.empty());

        assertThrows(ClaimNotFoundException.class,
                () -> claimService.delete(noExistingId));
    }
}
