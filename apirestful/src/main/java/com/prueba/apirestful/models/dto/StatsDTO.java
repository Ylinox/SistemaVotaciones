package com.prueba.apirestful.models.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsDTO {
    private long totalVotes;
    private List<CandidateStatsDTO> candidateStats;
}
