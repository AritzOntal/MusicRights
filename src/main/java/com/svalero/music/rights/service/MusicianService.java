package com.svalero.music.rights.service;


import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.domain.Work;
import com.svalero.music.rights.exception.WorkNotFoundException;
import com.svalero.music.rights.repository.MusicianRepository;
import com.svalero.music.rights.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MusicianService {

    private MusicianRepository musicianRepository;
    private WorkRepository workRepository; //REFERENCIA AL REPOSITORIO POR AUTOWIRED

    public MusicianService(MusicianRepository musicianRepository, WorkRepository workRepository) { //MEJOR CON CONSTRUCTOR QUE CON AUTOWIRED
        this.musicianRepository = musicianRepository;
        this.workRepository = workRepository;
    }

    public void add(Musician musician) {
        musicianRepository.save(musician);
    }

    public List<Musician> findALl() {
        List<Musician> allMusicians = musicianRepository.findAll();
        return allMusicians;
    }

    public Musician findById(Long id) {
        Musician musician = musicianRepository.findById(id).orElse(null); //ME OLBIGA A PONER NULL PORQUE DEVUELVE UN OPTIONAL
        return musician;
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

    public void delete(long id) {
        musicianRepository.deleteById(id);
    }

    //FILTRADOS
    public List<Musician> findByWorkId(long workId) throws WorkNotFoundException {
        workRepository.findById(workId)
                .orElseThrow(WorkNotFoundException::new);

            List<Musician> musicians = musicianRepository.findMusiciansByWork(workId);
            return musicians;
        }
}

//EN ESTA CLASE PROGRAMO PARA LA BASE DE DATOS (CAPA LÓGICA DONDE, ES LO MÁS LIBRE)



