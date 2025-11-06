package com.svalero.music.rights.repository;

import com.svalero.music.rights.domain.Musician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Long> {

}

//LA CLASE PADRE DE LA EXTIENDE YA CREA UN CRUD AUTOMATICO PARA MIS CLASES

//ESTA CLASE SOLO ES UN INTERFACE, SE UTILIZA DESDE OTRAS PARTES PARA CONECTAR

//PARA QUE LOS METODOS DEVUELVAN LISTA TIENE QUE SER "JpaRepository" no CrudRepository