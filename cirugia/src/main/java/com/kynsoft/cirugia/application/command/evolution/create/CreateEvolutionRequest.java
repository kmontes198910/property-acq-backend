package com.kynsoft.cirugia.application.command.evolution.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEvolutionRequest {
    private UUID surgeryId;
    private String therapeuticFluids;
    private String prescriptionFluids;
    private String generalCare;
    private String monitoring;
    private String diet;
    private String analytics;
    private String others;
}