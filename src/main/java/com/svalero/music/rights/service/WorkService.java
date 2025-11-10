package com.svalero.music.rights.service;

import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.domain.Work;
import com.svalero.music.rights.repository.WorkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkService {

    private final WorkRepository workRepository;

    public WorkService (WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    public void add(Work work) {
        workRepository.save(work);
    }

    public List<Work> findAll() {
        List <Work> work = workRepository.findAll();
        return work;
    }

    public Work findById(Long id) {
        Work work = workRepository.findById(id).
                orElseThrow(() -> new RuntimeException("work_not_found"));
        return work;
    }

    public List<Work> findByMusician(Long id) {
        List <Work> works = workRepository.findByMusicianId(id);
        return works;
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
}
