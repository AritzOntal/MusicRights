package com.svalero.music.rights.controller;

import com.svalero.music.rights.domain.Claim;
import com.svalero.music.rights.domain.Work;
import com.svalero.music.rights.exception.ErrorResponse;
import com.svalero.music.rights.exception.MusicianNotFoundException;
import com.svalero.music.rights.exception.WorkNotFoundException;
import com.svalero.music.rights.service.DocumentService;
import com.svalero.music.rights.service.WorkService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService  workService) {
        this.workService = workService;

    }

    @GetMapping("/works")
        public ResponseEntity <List<Work>> getAll(
            @RequestParam(value = "duration", defaultValue = "") Float duration,
            @RequestParam(value = "composedAt", defaultValue = "") LocalDate composedAt,
            @RequestParam(value = "registred", defaultValue = "") Boolean registred
    ) {
        List<Work> works;

        if (duration != null & composedAt != null & registred != null) {

            works = workService.findByParameters(duration, composedAt, registred);

            return new ResponseEntity<>(works, HttpStatus.OK);

        } else {
            works = workService.findAll();
            return ResponseEntity.ok().body(works);
        }
    }

    @GetMapping("/works/{id}")
    public ResponseEntity <Work> get(@RequestParam Long id) {
        Work work = workService.findById(id);
        return   ResponseEntity.ok().body(work);
    }

    @PostMapping("/works")
    public ResponseEntity <Work> create(@Valid @RequestBody Work work) {
        workService.add(work);
        return  ResponseEntity.ok().body(work);
    }

    @PutMapping("/works/{id}")
    public ResponseEntity <Work> update(@Valid @PathVariable Long id, @RequestBody Work work) {
        workService.edit(id, work);
        return  ResponseEntity.ok().body(work);
    }

    @DeleteMapping("/works/{id}")
    public ResponseEntity <Void> delete(@PathVariable Long id) {
        workService.delete(id);
        return  ResponseEntity.noContent().build();
    }

    //FILTRADO
    @GetMapping("/works/by-musician/{id}")
    public ResponseEntity <List<Work>> getByMusician(@PathVariable Long id) throws MusicianNotFoundException {
        List <Work> worksOfMusician = workService.findByMusician(id);
        return   ResponseEntity.ok().body(worksOfMusician);
    }
}
