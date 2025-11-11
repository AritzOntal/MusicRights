package com.svalero.music.rights.controller;

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

import java.util.List;

@RestController
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService  workService) {
        this.workService = workService;

    }

    @GetMapping("/works")
        public List<Work> getAll() {
        List<Work> allWorks = workService.findAll();
        return allWorks;
    }

    @GetMapping("/works/{id}")
    public Work get(@RequestParam Long id) {
        Work work = workService.findById(id);
        return work;
    }

    @PostMapping("/works")
    public void create(@Valid @RequestBody Work work) {
        workService.add(work);
    }

    @PutMapping("/works/{id}")
    public void update(@Valid @PathVariable Long id, @RequestBody Work work) {
        workService.edit(id, work);
    }

    @DeleteMapping("/works/{id}")
    public void delete(@PathVariable Long id) {
        workService.delete(id);
    }


    //FILTRADO
    @GetMapping("/works/by-musician/{id}")
    public List<Work> getByMusician(@PathVariable Long id) throws MusicianNotFoundException {
        List <Work> worksOfMusician = workService.findByMusician(id);
        return worksOfMusician;
    }

    @ExceptionHandler(WorkNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(WorkNotFoundException cnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, "Not-found", "La obra no existe");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MusicianNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(MusicianNotFoundException cnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, "Not-found", "El músico no existe");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
