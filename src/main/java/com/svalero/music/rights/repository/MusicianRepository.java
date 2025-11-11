package com.svalero.music.rights.repository;

import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.domain.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Long> {

    @Query("SELECT m FROM Work w JOIN w.musicians m WHERE w.id = :id")
    List<Musician> findMusiciansByWork(@Param("id") Long id);
}

//PARA QUE LOS METODOS DEVUELVAN LISTA TIENE QUE SER "JpaRepository" no CrudRepository