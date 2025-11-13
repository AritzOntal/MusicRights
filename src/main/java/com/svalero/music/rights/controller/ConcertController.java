package com.svalero.music.rights.controller;

import com.svalero.music.rights.domain.Claim;
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
    public ResponseEntity<List<Concert>> getAll(
        @RequestParam(value = "city", required = false) String city,
        @RequestParam(value = "status", required = false) String status,
        @RequestParam(value = "performed", required = false) Boolean performed
    ) {
            List<Concert> concert;

            if ((city != null && !city.isBlank()) & (status != null && !status.isBlank()) & (performed != null)) {
                concert = concertService.findByParameters(city, status, performed);

                return new ResponseEntity<>(concert, HttpStatus.OK);

            } else {
                concert = concertService.findAll();
                return ResponseEntity.ok().body(concert);
            }
    }


    @GetMapping("/concerts/{id}")
    public ResponseEntity<Concert> get(long id) {
        Concert concert = concertService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(concert);
    }

    @PostMapping("/concerts")
    public ResponseEntity<Concert> create(@RequestBody Concert concert) {
        concertService.add(concert);
        return ResponseEntity.status(HttpStatus.CREATED).body(concert);
    }

    @PutMapping("concerts/{id}")
    public ResponseEntity<Concert> update(@RequestBody Concert concert, @PathVariable long id) {
        Concert updatedConcert = concertService.edit(id, concert);
        return ResponseEntity.status(HttpStatus.OK).body(updatedConcert);
    }

    @DeleteMapping("/concerts/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        concertService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    //FILTRADO JPQL
    @GetMapping("concerts/by-musician/{id}")
    public ResponseEntity<List<Concert>> getByMusician(@PathVariable Long id) {
        List<Concert> concertsOfMusician = concertService.findAllbyMusicianId(id);
        return ResponseEntity.status(HttpStatus.OK).body(concertsOfMusician);
    }
}