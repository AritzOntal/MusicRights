package com.svalero.music.rights.service;


import com.svalero.music.rights.domain.Claim;
import com.svalero.music.rights.exception.ClaimNotFoundException;
import com.svalero.music.rights.repository.ClaimRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimService {

    ClaimRepository claimRepository;

    public ClaimService (ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    public Claim add(Claim claim) {
        return claimRepository.save(claim);
    }

    public List<Claim> findAll() {
        return claimRepository.findAll();
    }

    public Claim findById(Long id) {
        return claimRepository.findById(id).orElse(null);
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

    public List <Claim> findByMusicianId(long id) {
        List <Claim> claims = claimRepository.findByMusicianId(id);
        return claims;
    }

}
