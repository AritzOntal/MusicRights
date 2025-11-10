package com.svalero.music.rights.controller;

import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.domain.Work;
import com.svalero.music.rights.service.MusicianService;
import com.svalero.music.rights.service.WorkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MusicianController {


    private final MusicianService musicianService;

    public MusicianController(MusicianService musicianService, WorkService workService) {
        this.musicianService = musicianService;
    }

    @GetMapping("/musicians")
    public List<Musician> getALl() {
        List<Musician> allMusicians = musicianService.findALl();
        return allMusicians;
    }

    @GetMapping("/musicians/{id}")
    public Musician getOne(@PathVariable Long id) {
        Musician musician =  musicianService.findById(id);
        return musician;
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


    //FILTRADOS
    @GetMapping("/musicians/by-work/{id}")
    public List<Work> getWorks(@PathVariable long id) {
        List <Work> musiciansOfWorks = musicianService.findByMusicianId(id);
        return musiciansOfWorks;
    }
}