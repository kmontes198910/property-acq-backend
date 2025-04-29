package com.kynsoft.cirugia.infrastructure.service;

import com.kynsoft.cirugia.domain.dto.MedicalTeam;
import com.kynsoft.cirugia.domain.service.IMedicalTeamService;
import com.kynsoft.cirugia.infrastructure.entities.MedicalTeamEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.MedicalTeamWriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalTeamService implements IMedicalTeamService {

    private final MedicalTeamWriteRepository medicalTeamWriteRepository;

    @Override
    @Transactional
    public UUID createMedicalTeam(MedicalTeam medicalTeam) {
        log.info("Creating medical team member with ID: {}", medicalTeam.getId());

        MedicalTeamEntity entity = MedicalTeamEntity.builder()
                .id(medicalTeam.getId())
                .surgeryId(medicalTeam.getSurgeryId())
                .memberId(medicalTeam.getMemberId())
                .memberName(medicalTeam.getMemberName())
                .memberLastName(medicalTeam.getMemberLastName())
                .specialtyName(medicalTeam.getSpecialtyName())
                .specialtyCode(medicalTeam.getSpecialtyCode())
                .role(medicalTeam.getRole())
                .businessId(medicalTeam.getBusinessId())
                .createdAt(LocalDateTime.now())
                .createdBy(medicalTeam.getCreatedBy())
                .build();

        MedicalTeamEntity savedEntity = medicalTeamWriteRepository.save(entity);
        return savedEntity.getId();
    }
    
    @Override
    @Transactional
    public void deleteMedicalTeam(UUID id) {
        log.info("Deleting medical team member with ID: {}", id);
        medicalTeamWriteRepository.deleteById(id);
    }
}