package com.svalero.music.rights.service;


import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.domain.Work;
import com.svalero.music.rights.exception.WorkNotFoundException;
import com.svalero.music.rights.repository.MusicianRepository;
import com.svalero.music.rights.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MusicianService {

    private MusicianRepository musicianRepository;
    private WorkRepository workRepository; //REFERENCIA AL REPOSITORIO POR AUTOWIRED

    public MusicianService(MusicianRepository musicianRepository, WorkRepository workRepository) { //MEJOR CON CONSTRUCTOR QUE CON AUTOWIRED
        this.musicianRepository = musicianRepository;
        this.workRepository = workRepository;
    }

    //TODO ENTEDER COMO LO METE TODO EN LA TABLA N a N
    public Musician add(Musician musician) {
        List<Work> managedWorks = new ArrayList<>();

        if (musician.getWorks() != null) {      //musician.getWorks ---> ids que el cliente dice que quiere relacionar (los del Array)
            for (Work w : musician.getWorks()) {
                Work workDb = workRepository.findById(w.getId()) //Saca el ID de cada Work pedido por el cliente y lo checkea en BDD
                        .orElseThrow(() -> new RuntimeException("Work not found")); //Si no encuentra nigun lanza exception

                workDb.getMusicians().add(musician);  // Como Work es el propietario de la talba intermedia, le añadimos el músico
                managedWorks.add(workDb); //A la lista creada arriba le añadimos el work
            }
        }
        musician.setWorks(managedWorks); //Al músico le añadimos la lista de works
        Musician saved = musicianRepository.save(musician);
        return saved;
    }

    public ResponseEntity<List<Musician>> findAll(Float performanceFee, Boolean affiliated, LocalDate birthDate) {
        List<Musician> musician;

        if (performanceFee != null && affiliated != null && birthDate != null) {
            musician = musicianRepository.findByPerformanceFeeAndAffiliatedAndBirthDate(performanceFee, affiliated, birthDate);
            return new ResponseEntity<>(musician, HttpStatus.OK);

        } else {
            musician = musicianRepository.findAll();
            return new ResponseEntity<>(musician, HttpStatus.OK);
        }
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



