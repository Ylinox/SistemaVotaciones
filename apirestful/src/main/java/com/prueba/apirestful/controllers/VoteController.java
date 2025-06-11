package com.prueba.apirestful.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.apirestful.models.Vote;
import com.prueba.apirestful.models.dto.StatsDTO;
import com.prueba.apirestful.services.VoteService;

@RestController
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<Vote> createVote(@RequestBody Vote vote) {
        Vote saved = voteService.createVoteAndUpdateEntities(vote);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
    @GetMapping
    public ResponseEntity<List<Vote>> getAllVote() {
        List<Vote> listVote = voteService.getAllVote();
        return ResponseEntity.ok(listVote);
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatsDTO> getMethodName() {
        StatsDTO statsDTO = voteService.getVoteStastics();
        return ResponseEntity.ok(statsDTO);
    }
    
}
