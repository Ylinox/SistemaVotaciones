package com.prueba.apirestful.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.prueba.apirestful.repositories.CandidateRepository;
import com.prueba.apirestful.repositories.VoterRepository;
import com.prueba.apirestful.securities.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;
    private final JwtService jwtService;

    public AuthController(VoterRepository voterRepository, JwtService jwtService, CandidateRepository candidateRepository) {
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (voterRepository.existsByEmail(email)) {
        String token = jwtService.generateToken(email);
        return ResponseEntity.ok(Map.of("token", token, "type", "voter"));
        } else if (candidateRepository.existsByEmail(email)) {
            String token = jwtService.generateToken(email);
            return ResponseEntity.ok(Map.of("token", token, "type", "candidate"));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email no registrado");
        }
    }
}
