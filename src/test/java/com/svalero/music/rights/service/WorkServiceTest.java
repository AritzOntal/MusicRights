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
public class WorkServiceTest {

    @InjectMocks
    private WorkService workService;
    @Mock
    private WorkRepository workRepository;
    @Mock
    private MusicianRepository musicianRepository;


    //FIND ALL

    @Test
    void findAllOK() {

        Musician m1 = new Musician(1L, "Freddie", "Mercury", LocalDate.of(1946, 9, 5), true, "12345678A", 500.0f, 1001L, null, null);
        Musician m2 = new Musician(2L, "Brian", "May", LocalDate.of(1947, 7, 19), true, "87654321B", 450.0f, 1002L, null, null);
        List<Musician> musicians = List.of(m1, m2);

        List<Work> workList = List.of(
                new Work(1L, "Gravity", "USABC2300012", "Blues", 4.21f, LocalDate.of(2005, 9, 1), true, musicians),
                new Work(2L, "Everlong", "USFOO2300543", "Rock", 4.50f, LocalDate.of(1997, 8, 18), true, musicians)
        );

        when(workRepository.findAll()).thenReturn(workList);

        ResponseEntity<List<Work>> result = workService.findAll(null, null, null);

        List<Work> documentList = result.getBody();
        assertEquals(2, documentList.size());

    }

    //ADD

    @Test
    void testAddOk() {

        Long existId = 1L;

        Musician m1 = new Musician(existId, "Freddie", "Mercury", LocalDate.of(1946, 9, 5), true, "12345678A", 500.0f, 1001L, null, null);
        List<Musician> musicians = List.of(m1);

        Work work = new Work(1L, "Gravity", "USABC2300012", "Blues", 4.21f, LocalDate.of(2005, 9, 1), true, musicians);


        when(musicianRepository.findById(existId))
                .thenReturn(Optional.of(musicians.get(0)));

        when(workRepository.save(any(Work.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Work result = workService.add(work);
        assertSame(work, result);

        verify(workRepository, times(1)).save(work);
    }


    @Test
    void testAddWorkNotFound() {
        Long noExistingId = 1L;

        Work work = new Work();
        List<Musician> musicians = List.of
                (new Musician(noExistingId, "Freddie", "Mercury", LocalDate.of(1946, 9, 5), true, "12345678A", 500.0f, 1001L, null, null));
        work.setMusicians(musicians);

        when(musicianRepository.findById(noExistingId)).thenReturn(Optional.empty());

        assertThrows(MusicianNotFoundException.class,
                () -> workService.add(work));
    }

    //FIND BY ID

    @Test
    void testFindByIdOk() {

        Long existingId = 1L;
        Work work = new Work();
        work.setId(existingId);

        when(workRepository.findById(existingId))
                .thenReturn(Optional.of(work));

        Work result = workService.findById(existingId);
        assertSame(work, result);
    }

    @Test
    void testFindByIdException() {
        Long notExistingId = 99L;

        when(workRepository.findById(notExistingId))
                .thenReturn(Optional.empty());

        assertThrows(WorkNotFoundException.class,
                () -> workService.findById(notExistingId));
    }

    //EDIT

    @Test
    void testEditOK() {
        Long idWork = 1L;
        Work workDb = new Work();
        workDb.setId(idWork);

        Work newWork = new Work();
        newWork.setGenre("Metal");

        when(workRepository.findById(idWork))
                .thenReturn(Optional.of(workDb));

        when(workRepository.save(any(Work.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        ResponseEntity<Work> result = workService.edit(idWork, newWork);
        assertSame(workDb, result.getBody());
    }

    @Test
    void testModifyNotFound() {
        Long noExist = 1L;
        Work workDb = new Work();
        workDb.setId(noExist);

        Work newWork = new Work();
        newWork.setGenre("test");

        when(workRepository.findById(noExist))
                .thenReturn(Optional.empty());

        assertThrows(WorkNotFoundException.class,
                () -> workService.edit(noExist, newWork));
    }


    //DELETE

    @Test
    void testDeleteOk() {
        Long existId = 1L;
        Work work = new Work();
        work.setId(existId);

        when(workRepository.findById(existId))
                .thenReturn(Optional.of(work));

        workService.delete(existId);

        verify(workRepository, times(1)).delete(work);
    }

    @Test
    void testDeleteNotFound() {
        Long noExistingId = 1L;

        when(workRepository.findById(noExistingId))
                .thenReturn(Optional.empty());

        assertThrows(WorkNotFoundException.class,
                () -> workService.delete(noExistingId));
    }
}


