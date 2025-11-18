package com.svalero.music.rights.service;

import com.svalero.music.rights.domain.Claim;
import com.svalero.music.rights.domain.Concert;
import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.domain.Work;
import com.svalero.music.rights.exception.ClaimNotFoundException;
import com.svalero.music.rights.exception.ConcertNotFoundException;
import com.svalero.music.rights.exception.MusicianNotFoundException;
import com.svalero.music.rights.exception.WorkNotFoundException;
import com.svalero.music.rights.repository.ClaimRepository;
import com.svalero.music.rights.repository.ConcertRepository;
import com.svalero.music.rights.repository.MusicianRepository;
import com.svalero.music.rights.repository.WorkRepository;
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
public class ConcertServiceTest {

    @InjectMocks
    private ConcertService concertService;
    @Mock
    private ConcertRepository concertRepository;
    @Mock
    private MusicianRepository musicianRepository;


    //FIND ALL

    @Test
    void findAllOK() {

        Musician musician = new Musician();
        List<Concert> concerts = List.of(
                new Concert(1L, "Rock Fest", "Bilbao", "Bizkaia", LocalDate.of(2026, 7, 12), "OPEN", true, 25.0f, -2.934985, 43.263012, musician),
                new Concert(2L, "Metal Night", "Madrid", "Madrid", LocalDate.of(2026, 9, 20), "CLOSED", false, 30.0f, -3.703790, 40.416775, musician)
        );

        when(concertRepository.findAll()).thenReturn(concerts);

        ResponseEntity<List<Concert>> result = concertService.findAll(null, null, null);

        List<Concert> resultBody = result.getBody();
        assertEquals(2, resultBody.size());

    }

    //ADD

    @Test
    void testAddOk() {
        Musician musician = new Musician();
        Concert concert  = new Concert(1L, "Rock Fest", "Bilbao", "Bizkaia", LocalDate.of(2026, 7, 12), "OPEN", true, 25.0f, -2.934985, 43.263012, musician);

        when(concertRepository.save(any(Concert.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Concert result = concertService.add(concert);
        assertSame(concert, result);

        verify(concertRepository, times(1)).save(concert);
    }


    @Test
    void testAddConcertNotFound() {
        Long noExistingId = 1L;
        Concert concert = new Concert();
        Musician musicianMock = new Musician();
        musicianMock.setId(noExistingId);
        concert.setMusician(musicianMock);

        when(musicianRepository.findById(noExistingId))
                .thenReturn(Optional.empty());

        assertThrows(MusicianNotFoundException.class,
                () -> concertService.add(concert));
    }

    //FIND BY ID

    @Test
    void testFindByIdOk() {

        Long existingId = 1L;
        Concert concert = new Concert();
        concert.setId(existingId);

        when(concertRepository.findById(existingId))
                .thenReturn(Optional.of(concert));

        Concert result = concertService.findById(existingId);
        assertSame(concert, result);
    }

    @Test
    void testFindByIdException() {
        Long notExistingId = 99L;

        when(concertRepository.findById(notExistingId))
                .thenReturn(Optional.empty());

        assertThrows(ConcertNotFoundException.class,
                () -> concertService.findById(notExistingId));
    }

    //EDIT

    @Test
    void testEditOK() {
        Long existingId = 1L;
        Concert concertDb = new Concert();
        concertDb.setId(existingId);

        Concert newConcert = new Concert();
        newConcert.setProvince("Zaragoza");

        when(concertRepository.findById(existingId))
                .thenReturn(Optional.of(concertDb));

        when(concertRepository.save(any(Concert.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Concert result = concertService.edit(existingId, newConcert);
        assertSame(concertDb, result);
    }

    @Test
    void testModifyNotFound() {
        Long noExist = 1L;
        Concert concertDb = new Concert();
        concertDb.setId(noExist);

        Concert newConcert = new Concert();
        newConcert.setProvince("Zaragoza");

        when(concertRepository.findById(noExist))
                .thenReturn(Optional.empty());

        assertThrows(ConcertNotFoundException.class,
                () -> concertService.edit(noExist, newConcert));
    }


    //DELETE

    @Test
    void testDeleteOk() {
        Long existId = 1L;

        when(concertRepository.findById(existId))
                .thenReturn(Optional.of(new Concert()));

        concertService.delete(existId);
        verify(concertRepository, times(1)).deleteById(existId);
    }

    @Test
    void testDeleteNotFound() {
        Long noExistingId = 1L;

        when(concertRepository.findById(noExistingId))
                .thenReturn(Optional.empty());

        assertThrows(ConcertNotFoundException.class,
                () -> concertService.delete(noExistingId));
    }
}

