package com.svalero.music.rights.service;


import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.repository.MusicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MusicianService {

    @Autowired
    private MusicianRepository musicianRepository; //REFERENCIA AL REPOSITORIO POR AUTOWIRED

    public void add(Musician musician) {
        musicianRepository.save(musician);
    }

    public List<Musician> findALl() {
        List<Musician> allMusicians = musicianRepository.findAll();
        return allMusicians;
    }

    public void delete(long id) {
        musicianRepository.deleteById(id);
    }

    public Musician edit(long id, Musician updatedMusician) {
        Musician musician = musicianRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("musician_not_found"));

        musician.setFirstName(updatedMusician.getFirstName());
        musician.setLastName(updatedMusician.getLastName());
        musician.setBirthDate(updatedMusician.getBirthDate());
        musician.setAffiliated(updatedMusician.isAffiliated());
        musician.setDni(updatedMusician.getDni());
        musician.setAffiliatedNumber(updatedMusician.getAffiliatedNumber());

        musicianRepository.save(musician);
        return musician;
    }

}
//EN ESTA CLASE PROGRAMO PARA LA BASE DE DATOS (CAPA LÓGICA DONDE, ES LO MÁS LIBRE)


