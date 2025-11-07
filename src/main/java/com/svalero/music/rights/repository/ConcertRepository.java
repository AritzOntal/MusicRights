package com.svalero.music.rights.repository;

import com.svalero.music.rights.domain.Concert;
import com.svalero.music.rights.domain.Musician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    List<Concert> findAllByMusician(Musician musician);
}
