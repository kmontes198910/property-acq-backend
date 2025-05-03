package com.kynsoft.cirugia.application.command.intraOperative.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateIntraOperativeRequest {
    private UUID surgeryId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String procedureType;
    private String anesthesiaType;
    private String projectedProcedure;
    private String performedProcedure;
    private String dieresis;
    private String expositionExploration;
    private String surgicalFindings;
    private Integer bloodLoss;
    private Integer approximateBlood;
    private Boolean prostheticMaterial;
    private String surgicalProcedure;
    private String description;
}