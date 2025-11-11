package com.svalero.music.rights.repository;

import com.svalero.music.rights.domain.Claim;
import com.svalero.music.rights.domain.Document;
import com.svalero.music.rights.domain.Musician;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//TODO HACER LA CONSULTA RELACION JOIN

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query ("SELECT c FROM Document d JOIN d.claim c WHERE c.id = :id")
    Document findByClaimId(@Param ("id") Long claimId);
}
