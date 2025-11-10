package com.svalero.music.rights.repository;

import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.domain.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {

    List<Work> findByMusicianId(Long musicianId);
}
