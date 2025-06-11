package com.prueba.apirestful.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.prueba.apirestful.models.Candidate;
import com.prueba.apirestful.models.Vote;
import com.prueba.apirestful.models.Voter;
import com.prueba.apirestful.models.dto.CandidateStatsDTO;
import com.prueba.apirestful.models.dto.StatsDTO;
import com.prueba.apirestful.repositories.CandidateRepository;
import com.prueba.apirestful.repositories.VoteRepository;
import com.prueba.apirestful.repositories.VoterRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final CandidateRepository candidateRepository;
    private final VoterRepository voterRepository;

    public VoteService(VoteRepository voteRepository, CandidateRepository candidateRepository, VoterRepository voterRepository) {
        this.voteRepository = voteRepository;
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
    }

    @Transactional
    public Vote createVoteAndUpdateEntities(Vote vote){
        Voter existingVoter = voterRepository.findById(vote.getVoterId())
        .orElseThrow(() -> new EntityNotFoundException("Votante no encontrado con ID: " + vote.getVoterId()));
        if (Boolean.TRUE.equals(existingVoter.getHasVoted())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El votante ya ha emitido su voto");
        }
        Candidate existingCandidate = candidateRepository.findById(vote.getCandidateId())
        .orElseThrow(() -> new EntityNotFoundException("Candidato no encontrado con ID: " + vote.getCandidateId()));

        Vote saved = voteRepository.save(vote);

        Integer votes = existingCandidate.getVotes();
        existingCandidate.setVotes(++votes);
        candidateRepository.save(existingCandidate);

        existingVoter.setHasVoted(true);
        voterRepository.save(existingVoter);

        return saved;
    }

    public List<Vote> getAllVote(){
        return voteRepository.findAll();
    }

    public StatsDTO getVoteStastics(){
        long totalVotes = voteRepository.count();
        List<Candidate> candidates = candidateRepository.findAll();
        List<CandidateStatsDTO> stats = new ArrayList<>();
        for (Candidate candidate : candidates) {
            int candidateVotes = candidate.getVotes();
            double percentage = totalVotes == 0 ? 0 : (double) candidateVotes * 100 / totalVotes;
            stats.add(new CandidateStatsDTO(candidate.getName(), candidateVotes, percentage));
        }
        return new StatsDTO(totalVotes, stats);
    }
} 


