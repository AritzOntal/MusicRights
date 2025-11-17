package com.svalero.music.rights.repository;

import com.svalero.music.rights.domain.Musician;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Long> {

    List<Musician> findByPerformanceFeeAndAffiliatedAndBirthDate(float perfomanceFee, Boolean affiliated, LocalDate birthDate);
}
