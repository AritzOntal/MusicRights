package com.svalero.music.rights.repository;

import com.svalero.music.rights.domain.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

    public List <Claim> findByMusicianId(long claimId);
}
