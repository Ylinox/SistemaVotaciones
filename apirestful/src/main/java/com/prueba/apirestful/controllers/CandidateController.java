package com.prueba.apirestful.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.apirestful.models.Candidate;
import com.prueba.apirestful.services.CandidateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidates")
public class CandidateController {
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    public ResponseEntity<Candidate> createVoter(@Valid @RequestBody Candidate candidate) {
        Candidate saved = candidateService.createCandidate(candidate);        
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
    @GetMapping
    public ResponseEntity<Page<Candidate>> getAllVoters(@RequestParam(required = false) String name, Pageable pageable) {
        Page<Candidate> candidate;
        if (name != null && !name.isEmpty()) {
        candidate = candidateService.getByNameContaining(name, pageable);
        } else {
            candidate = candidateService.getAllCandidates(pageable);
        }
        return ResponseEntity.ok(candidate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getVoterById(@PathVariable Integer id) {
        Candidate candidate = candidateService.getCandidateById(id);
        return ResponseEntity.ok(candidate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoterById(@PathVariable Integer id){
        candidateService.deleteCandidateById(id);
        return ResponseEntity.noContent().build();
    }
}
