package com.svalero.music.rights.controller;

import com.svalero.music.rights.domain.Work;
import com.svalero.music.rights.service.DocumentService;
import com.svalero.music.rights.service.WorkService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WorkController {

    private final DocumentService documentService;
    WorkService workService;

    public WorkController(WorkService  workService, DocumentService documentService) {
        this.workService = workService;
        this.documentService = documentService;
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
    public List<Work> getByMuscian(@PathVariable Long id) {
        List <Work> worksOfMusician = workService.findByMusician(id);
        return worksOfMusician;
    }

}
