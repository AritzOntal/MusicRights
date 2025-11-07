package com.svalero.music.rights.repository;

import com.svalero.music.rights.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {

}
