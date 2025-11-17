package com.svalero.music.rights.repository;

import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.domain.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//TODO HACER LA CONSULTA RELACION JOIN

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

    @Query ("SELECT w FROM Work w JOIN w.musicians m WHERE m.id = :id")

    List<Work> findByMusicianId(@Param ("id")Long musicianId);

    List<Work> findByDurationAndComposedAtAndRegistred(Float duration, LocalDate composedAt, Boolean registred);

}
