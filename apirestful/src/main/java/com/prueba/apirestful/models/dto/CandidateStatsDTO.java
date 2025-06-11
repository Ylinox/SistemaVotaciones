package com.prueba.apirestful.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateStatsDTO {
    private String name;
    private int votes;
    private double percentage;
}
