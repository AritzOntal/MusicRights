package com.svalero.music.rights.repository;

import com.svalero.music.rights.domain.Claim;
import com.svalero.music.rights.domain.Document;
import com.svalero.music.rights.domain.Musician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findAllByClaim(Claim claim);

}
