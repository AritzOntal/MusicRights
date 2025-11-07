package com.svalero.music.rights.service;

import com.svalero.music.rights.domain.Concert;
import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.repository.ConcertRepository;
import com.svalero.music.rights.repository.MusicianRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    private ConcertRepository concertRepository;

    public ConcertService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    public void add(Concert concert) {
        concertRepository.save(concert);
    }

    public Concert findById(long id) {
        concertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("concert_not_found"));
        return concertRepository.findById(id).get();
    }

    public List<Concert> findAllbyMusician(Musician musician) {
        List<Concert> allConcerts = concertRepository.findAllByMusician(musician);
        return allConcerts;
    }
    //LLAMADA PARA RETORNAR TODOS LOS CONCIERTOS DE UN MUSICO

    public Concert edit(long id, Concert updateConcert) {
        Concert concert = concertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("concert_not_found"));
        concert.setShowTitle(updateConcert.getShowTitle());
        concert.setCity(updateConcert.getCity());
        concert.setLongitude(updateConcert.getLongitude());
        concert.setLatitude(updateConcert.getLatitude());
        concert.setStatus(updateConcert.getStatus());
        concert.setMusician(updateConcert.getMusician());
        concert.setDate(updateConcert.getDate());

        concertRepository.save(concert);
        return concert;
    }










}
