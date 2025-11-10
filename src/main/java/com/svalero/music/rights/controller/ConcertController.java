package com.svalero.music.rights.controller;

import com.svalero.music.rights.domain.Concert;
import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.service.ConcertService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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

    @GetMapping("/concerts{id}")
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

    //FILTRADO
    @GetMapping("concerts/by-musician/{id}")
    public List<Concert> getByMusician(@PathVariable Long id) {
        List <Concert> concertsOfMusician = concertService.findAllbyMusicianId(id);
        return concertsOfMusician;
    }

}
