package com.svalero.music.rights.repository;

import com.svalero.music.rights.domain.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

}
