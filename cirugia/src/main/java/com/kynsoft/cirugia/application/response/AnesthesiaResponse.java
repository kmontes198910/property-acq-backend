package com.kynsoft.cirugia.application.response;

import com.kynsoft.cirugia.domain.dto.Anesthesia;
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
public class AnesthesiaResponse {
    private UUID id;
    private UUID surgeryId;
    private String anesthesiaType;
    private Double mouthOpening;
    private Double thyromental_distance;
    private String mallampati;
    private String cervicalMobility;
    private String mandibularProtrusion;
    private Boolean difficultIntubationHistory;
    private String intubationDifficulties;
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
    
    public AnesthesiaResponse(Anesthesia anesthesia) {
        if (anesthesia != null) {
            this.id = anesthesia.getId();
            this.surgeryId = anesthesia.getSurgeryId();
            this.anesthesiaType = anesthesia.getAnesthesiaType();
            this.mouthOpening = anesthesia.getMouthOpening();
            this.thyromental_distance = anesthesia.getThyromental_distance();
            this.mallampati = anesthesia.getMallampati();
            this.cervicalMobility = anesthesia.getCervicalMobility();
            this.mandibularProtrusion = anesthesia.getMandibularProtrusion();
            this.difficultIntubationHistory = anesthesia.getDifficultIntubationHistory();
            this.intubationDifficulties = anesthesia.getIntubationDifficulties();
            this.thoraxDescription = anesthesia.getThoraxDescription();
            this.heartDescription = anesthesia.getHeartDescription();
            this.lungsDescription = anesthesia.getLungsDescription();
            this.abdomenDescription = anesthesia.getAbdomenDescription();
            this.extremitiesDescription = anesthesia.getExtremitiesDescription();
            this.centralNervousSystem = anesthesia.getCentralNervousSystem();
            this.asaPhysicalStatus = anesthesia.getAsaPhysicalStatus();
            this.operationRisks = anesthesia.getOperationRisks();
            this.metabolicEquivalent = anesthesia.getMetabolicEquivalent();
            this.lastIntoxicationHours = anesthesia.getLastIntoxicationHours();
            this.anesthetics = anesthesia.getAnesthetics();
            this.surgicalDrugs = anesthesia.getSurgicalDrugs();
            this.allergies = anesthesia.getAllergies();
            this.transfusions = anesthesia.getTransfusions();
            this.habits = anesthesia.getHabits();
            this.createdAt = anesthesia.getCreatedAt();
            this.updatedAt = anesthesia.getUpdatedAt();
            this.createdBy = anesthesia.getCreatedBy();
            this.updatedBy = anesthesia.getUpdatedBy();
        }
    }
}