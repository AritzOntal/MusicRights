package com.svalero.music.rights.repository;

import com.svalero.music.rights.domain.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Long> {

}
