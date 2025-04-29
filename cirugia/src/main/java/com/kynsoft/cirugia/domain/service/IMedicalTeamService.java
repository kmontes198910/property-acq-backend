package com.kynsoft.cirugia.domain.service;

import com.kynsoft.cirugia.domain.dto.MedicalTeam;

import java.util.UUID;

public interface IMedicalTeamService {
    UUID createMedicalTeam(MedicalTeam medicalTeam);
    
    void deleteMedicalTeam(UUID id);
}