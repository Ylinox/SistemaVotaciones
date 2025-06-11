package com.prueba.apirestful.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.prueba.apirestful.models.Candidate;
import com.prueba.apirestful.repositories.CandidateRepository;
import com.prueba.apirestful.repositories.VoterRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final VoterRepository voterRepository;

    public CandidateService(CandidateRepository candidateRepository, VoterRepository voterRepository) {
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
    }

    public Candidate createCandidate(Candidate candidate){
        if (voterRepository.existsByEmail(candidate.getEmail())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este email ya está registrado como candidato");
        }
        return candidateRepository.save(candidate);
    } 

    public Page<Candidate> getAllCandidates(Pageable pageable){
        return candidateRepository.findAll(pageable);
    }

    public Page<Candidate> getByNameContaining(String name, Pageable pageable) {
    return candidateRepository.findByNameContaining(name, pageable);
    }

    public Candidate getCandidateById(Integer id){
        return candidateRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Candidato no encontrado con ID: " + id));
    }

    public void deleteCandidateById(Integer id){
        if(!candidateRepository.existsById(id)){
            throw new EntityNotFoundException("No se puede eliminar. El candidato con ID " + id + " no existe.");
        }
        candidateRepository.deleteById(id);
    }
    
}
