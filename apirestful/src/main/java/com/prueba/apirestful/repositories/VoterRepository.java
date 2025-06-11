package com.prueba.apirestful.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.apirestful.models.Voter;


@Repository
public interface VoterRepository extends JpaRepository <Voter, Integer> {
    Page<Voter> findByNameContaining(String name, Pageable pageable);
    Optional<Voter> findByEmail(String email);
    boolean existsByEmail(String email);
}
