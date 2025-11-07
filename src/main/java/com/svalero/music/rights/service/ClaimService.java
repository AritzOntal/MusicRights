package com.svalero.music.rights.service;


import com.svalero.music.rights.domain.Claim;
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

    public Claim findById(Long id) {
        return claimRepository.findById(id).orElse(null);
    }

    public List<Claim> findAll() {
        return claimRepository.findAll();
    }

    public void delete(long id) {
        claimRepository.deleteById(id);
    }

    public Claim update(long id, Claim updatedClaim) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("claim_not_found"));

        claim.setType(updatedClaim.getType());
        claim.setDescription(updatedClaim.getDescription());
        claim.setStatus(updatedClaim.getStatus());
        claim.setReference(updatedClaim.getReference());

        claimRepository.save(claim);
        return claim;
    }

}
