package com.kynsoft.cirugia.application.command.anesthesia.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAnesthesiaRequest {
    private String id;
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
}