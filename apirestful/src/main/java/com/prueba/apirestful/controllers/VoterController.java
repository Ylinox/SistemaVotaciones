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

import com.prueba.apirestful.models.Voter;
import com.prueba.apirestful.services.VoterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/voters")
public class VoterController {

    private final VoterService voterService;

    public VoterController(VoterService voterService) {
        this.voterService = voterService;
    }

    @PostMapping
    public ResponseEntity<Voter> createVoter(@Valid @RequestBody Voter voter) {
        Voter saved = voterService.createVoter(voter);        
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
    @GetMapping
    public ResponseEntity<Page<Voter>> getAllVoters(@RequestParam(required = false) String name, Pageable pageable) {
        Page<Voter> voters;
        if (name != null && !name.isEmpty()) {
        voters = voterService.getByNameContaining(name, pageable);
        } else {
            voters = voterService.getAllVoters(pageable);
        }
        return ResponseEntity.ok(voters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voter> getVoterById(@PathVariable Integer id) {
        Voter voter = voterService.getVoterById(id);
        return ResponseEntity.ok(voter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoterById(@PathVariable Integer id){
        voterService.deleteVoterById(id);
        return ResponseEntity.noContent().build();
    }
}
