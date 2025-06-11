package com.prueba.apirestful.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.apirestful.models.Candidate;


@Repository
public interface CandidateRepository extends JpaRepository <Candidate, Integer>{
    Page<Candidate> findByNameContaining(String name, Pageable pageable);
    Optional<Candidate> findByEmail(String email);
    boolean existsByEmail(String email);
}
