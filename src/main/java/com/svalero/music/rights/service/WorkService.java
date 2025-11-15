package com.svalero.music.rights.service;

import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.domain.Work;
import com.svalero.music.rights.exception.MusicianNotFoundException;
import com.svalero.music.rights.repository.MusicianRepository;
import com.svalero.music.rights.repository.WorkRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WorkService {

    private final WorkRepository workRepository;
    private final MusicianService musicianService;
    private final MusicianRepository musicianRepository;

    public WorkService(WorkRepository workRepository, MusicianService musicianService,  MusicianRepository musicianRepository) {
        this.workRepository = workRepository;
        this.musicianService = musicianService;
        this.musicianRepository = musicianRepository;
    }

    public void add(Work work) {
        workRepository.save(work);
    }

    public ResponseEntity<List<Work>> findAll(Float duration, LocalDate composedAt, Boolean registred) {

        List<Work> works;

        if (duration != null & composedAt != null & registred != null) {
            works = workRepository.findByDurationAndComposedAtAndRegistred(duration, composedAt, registred);
            return new ResponseEntity<>(works, HttpStatus.OK);

        } else {
            works = workRepository.findAll();
            return new ResponseEntity<>(works, HttpStatus.OK);
        }
    }

    public Work findById(Long id) {
        Work work = workRepository.findById(id).
                orElseThrow(() -> new RuntimeException("work_not_found"));
        return work;
    }

    public List<Work> findByMusician(Long id) throws MusicianNotFoundException {
        Musician musician = musicianService.findById(id);
        if (musician == null) {
            throw new MusicianNotFoundException();
        } else {
            List<Work> works = workRepository.findByMusicianId(id);
            return works;
        }
    }

    public Work edit(long id, Work updateWork) {
        Work work = findById(id);

        work.setDuration(updateWork.getDuration());
        work.setGenre(updateWork.getGenre());
        work.setTitle(updateWork.getTitle());
        work.setIsrc(updateWork.getIsrc());

        return work;
    }

    public void delete(long id) {
        workRepository.deleteById(id);
    }

    public Boolean existsById(Long id) {
        return workRepository.existsById(id);
    }
}
