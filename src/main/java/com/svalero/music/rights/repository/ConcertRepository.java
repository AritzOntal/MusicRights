package com.svalero.music.rights.repository;

import com.svalero.music.rights.domain.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

}
