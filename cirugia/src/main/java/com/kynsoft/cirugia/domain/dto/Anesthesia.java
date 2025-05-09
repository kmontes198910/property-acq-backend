package com.kynsoft.cirugia.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Anesthesia {
    private UUID id;
    private UUID surgeryId;
    private String anesthesiaType;
    private String mouthOpening;
    private Double thyromental_distance;
    private String mallampati;
    private String cervicalMobility;
    private String mandibularProtrusion;
    private Boolean difficultIntubationHistory;
    private Boolean intubationDifficulties;
    private String thoraxDescription;
    private String heartDescription;
    private String lungsDescription;
    private String abdomenDescription;
    private String extremitiesDescription;
    private String centralNervousSystem;
    private String asaPhysicalStatus;
    private String operationRisks;
    private String metabolicEquivalent;
    private Integer lastIntoxicationHours;
    private String anesthetics;
    private String surgicalDrugs;
    private String allergies;
    private String transfusions;
    private String habits;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}