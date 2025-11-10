package com.svalero.music.rights.controller;

import com.svalero.music.rights.domain.Claim;
import com.svalero.music.rights.domain.Concert;
import com.svalero.music.rights.repository.ClaimRepository;
import com.svalero.music.rights.service.ClaimService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService, ClaimRepository claimRepository) {
        this.claimService = claimService;
    }

@GetMapping("/claims")
public List<Claim> getAll() {
        List<Claim> claim = claimService.findAll();
        return claim;
}

@GetMapping("/claims{id}")
public Claim get(@PathVariable long id) {
        Claim claim = claimService.findById(id);
        return claim;
}

@PostMapping("/claims")
public void create (@RequestBody Claim claim) {
        claimService.add(claim);
    }

@PutMapping("claims/{id}")
    public void update (@RequestBody Claim claim, @PathVariable int id) {
        Claim updatedClaim = claimService.update(id, claim);
}

@DeleteMapping("claims/{id}")
public void remove (@PathVariable int id) {
        claimService.delete(id);
}

//FILTRADOS

@GetMapping("/claims/by-musician/{id}")
public List<Claim> getByMusician (@PathVariable long id) {
        List<Claim> claimsOfMusician = claimService.findByMusicianId(id);
        return claimsOfMusician;
    }

}
