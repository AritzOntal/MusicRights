package com.svalero.music.rights.service;


import com.svalero.music.rights.domain.Claim;
import com.svalero.music.rights.domain.Musician;
import com.svalero.music.rights.exception.ClaimNotFoundException;
import com.svalero.music.rights.exception.MusicianNotFoundException;
import com.svalero.music.rights.repository.ClaimRepository;
import com.svalero.music.rights.repository.MusicianRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimService {

    private final MusicianService musicianService;
    private ClaimRepository claimRepository;

    public ClaimService (ClaimRepository claimRepository, MusicianService musicianService) {
        this.claimRepository = claimRepository;
        this.musicianService = musicianService;
    }

    public Claim add(Claim claim) {
        return claimRepository.save(claim);
    }

    public ResponseEntity <List<Claim>> findAll(String status, String type, Boolean pending) {

            List<Claim> claims;

            if (pending != null & (status != null && !status.isBlank()) & (type != null && !type.isBlank())) {

                claims = claimRepository.findByStatusAndTypeAndPending(status, type, pending);
                return new ResponseEntity<>(claims, HttpStatus.OK);

            } else {
                claims = claimRepository.findAll();
                return new ResponseEntity<>(claims, HttpStatus.OK);
        }
    }

    public Claim findById(Long id) throws ClaimNotFoundException {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(ClaimNotFoundException::new);
        return claim;
    }

    public Claim modify(long id, Claim updatedClaim) throws ClaimNotFoundException {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(ClaimNotFoundException::new);

        claim.setType(updatedClaim.getType());
        claim.setDescription(updatedClaim.getDescription());
        claim.setStatus(updatedClaim.getStatus());
        claim.setReference(updatedClaim.getReference());

        claimRepository.save(claim);
        return claim;
    }

    public void delete(long id) throws ClaimNotFoundException {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(ClaimNotFoundException::new);

        claimRepository.delete(claim);
    }

    //FILTRADOS
    public List <Claim> findByMusicianId(long id) throws MusicianNotFoundException {
        Musician musician = musicianService.findById(id);
        if (musician == null) {
            throw new MusicianNotFoundException();
        } else {
            List<Claim> claims = claimRepository.findByMusicianId(id);
            return claims;
        }
    }
}
