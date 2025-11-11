package com.svalero.music.rights.controller;

import com.svalero.music.rights.domain.Concert;
import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.exception.ClaimNotFoundException;
import com.svalero.music.rights.exception.ConcertNotFoundException;
import com.svalero.music.rights.exception.ErrorResponse;
import com.svalero.music.rights.exception.MusicianNotFoundException;
import com.svalero.music.rights.service.ConcertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConcertController {

    private final ConcertService concertService;

    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }

    @GetMapping("/concerts")
    public List<Concert> getAll(){
        List<Concert> concerts = concertService.findAll();
        return concerts;
    }

    @GetMapping("/concerts/{id}")
    public Concert get(long id) {
        Concert concert = concertService.findById(id);
        return concert;
    }

    @PostMapping("/concerts")
    public void create(@RequestBody Concert concert) {
        concertService.add(concert);
    }

    @PutMapping("concerts/{id}")
    public void update(@RequestBody Concert concert, @PathVariable long id) {
        concertService.edit(id, concert);

    }

    @DeleteMapping("/concerts/{id}")
    public void delete(@PathVariable long id) {
        concertService.delete(id);
    }

    //FILTRADO JPQL
    @GetMapping("concerts/by-musician/{id}")
    public List<Concert> getByMusician(@PathVariable Long id) {
        List <Concert> concertsOfMusician = concertService.findAllbyMusicianId(id);
        return concertsOfMusician;
    }

    @ExceptionHandler(ConcertNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ConcertNotFoundException cnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, "Not-found", "El concierto no existe");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MusicianNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(MusicianNotFoundException mne) {
        ErrorResponse errorResponse = new ErrorResponse(404, "Not-found", "El músico no existe");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
