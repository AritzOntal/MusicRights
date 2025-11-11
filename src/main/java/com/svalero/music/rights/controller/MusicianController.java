package com.svalero.music.rights.controller;

import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.domain.Work;
import com.svalero.music.rights.exception.DocumentNotFoundException;
import com.svalero.music.rights.exception.ErrorResponse;
import com.svalero.music.rights.exception.MusicianNotFoundException;
import com.svalero.music.rights.exception.WorkNotFoundException;
import com.svalero.music.rights.service.MusicianService;
import com.svalero.music.rights.service.WorkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MusicianController {


    private final MusicianService musicianService;

    public MusicianController(MusicianService musicianService) {
        this.musicianService = musicianService;
    }

    @GetMapping("/musicians")
    public ResponseEntity<List<Musician>> getALl() {
        List<Musician> allMusicians = musicianService.findALl();
        return  ResponseEntity.ok().body(allMusicians);
    }

    @GetMapping("/musicians/{id}")
    public ResponseEntity <Musician> get(@PathVariable Long id) {
        Musician musician =  musicianService.findById(id);
        return  ResponseEntity.ok().body(musician);
    }

    @PostMapping("/musicians")
    public ResponseEntity <Musician> create(@RequestBody Musician musician) {
        musicianService.add(musician);
        return  ResponseEntity.status(HttpStatus.CREATED).body(musician);
    }

    @PutMapping("/musicians/{id}")
    public ResponseEntity <Musician> edit(@PathVariable long id, @RequestBody Musician musician) {
        musicianService.edit(id, musician);
        return  ResponseEntity.ok().body(musician);
    }

    @DeleteMapping("/musicians/{id}")
    public ResponseEntity <Void> delete(@PathVariable long id) {
        musicianService.delete(id);
        return  ResponseEntity.noContent().build();
    }

    //FILTRADOS
    @GetMapping("/musicians/by-work/{id}")
    public ResponseEntity <List<Musician>> getByWork(@PathVariable long id) throws WorkNotFoundException {
        List <Musician> musiciansOfWork = musicianService.findByWorkId(id);
        return  ResponseEntity.ok().body(musiciansOfWork);
    }
}