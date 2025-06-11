package com.prueba.apirestful.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.prueba.apirestful.models.Voter;
import com.prueba.apirestful.repositories.CandidateRepository;
import com.prueba.apirestful.repositories.VoterRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VoterService {

    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;

    public VoterService(VoterRepository voterRepository, CandidateRepository candidateRepository) {
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
    }

    public Voter createVoter (Voter voter){
        if (candidateRepository.existsByEmail(voter.getEmail())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este email ya está registrado como candidato");
        }
        return voterRepository.save(voter);
    }

    public Page<Voter> getAllVoters(Pageable pageable){
        return voterRepository.findAll(pageable);
    }

    public Page<Voter> getByNameContaining(String name, Pageable pageable) {
    return voterRepository.findByNameContaining(name, pageable);
    }

    public Voter getVoterById(Integer id){
        return voterRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Votante no encontrado con ID: " + id));
    }

    public void deleteVoterById(Integer id){
        if(!voterRepository.existsById(id)){
            throw new EntityNotFoundException("No se puede eliminar. El votante con ID " + id + " no existe.");
        }
        voterRepository.deleteById(id);
    }
}
