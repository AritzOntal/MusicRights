package com.svalero.music.rights.controller;

import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.service.MusicianService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MusicianController {


    private final MusicianService musicianService;

    public MusicianController(MusicianService musicianService) {
        this.musicianService = musicianService;
    }

    @GetMapping("/musicians")
    public List<Musician> getALl() {
        List<Musician> allMusicians = musicianService.findALl();
        return allMusicians;
    }

    @PostMapping("/musicians")
    public void addGame(@RequestBody Musician musician) {
        musicianService.add(musician);
    }

    @PutMapping("/musicians/{id}")
    public void modifyMusician(@PathVariable long id, @RequestBody Musician musician) {
        musicianService.edit(id, musician);
    }

    @DeleteMapping("/musicians/{id}")
    public void removeMusician(@PathVariable long id) {
        musicianService.delete(id);
    }

}


//@PATHVARIABLE, ANOTACIÓN PARA RECOGER LA VARIABLE QUE PASAN A LA URL
