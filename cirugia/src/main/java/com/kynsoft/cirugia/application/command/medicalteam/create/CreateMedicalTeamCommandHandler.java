package com.kynsoft.cirugia.application.command.medicalteam.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.IMedicalTeamService;
import com.kynsoft.cirugia.domain.dto.MedicalTeam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateMedicalTeamCommandHandler implements ICommandHandler<CreateMedicalTeamCommand> {

    private final IMedicalTeamService medicalTeamService;

    @Override
    @Transactional
    public void handle(CreateMedicalTeamCommand command) {
        log.info("Creating new medical team member for surgery: {}", command.getSurgeryId());

        MedicalTeam medicalTeam = MedicalTeam.builder()
                .id(command.getId())
                .surgeryId(command.getSurgeryId())
                .memberId(command.getMemberId())
                .memberName(command.getMemberName())
                .memberLastName(command.getMemberLastName())
                .specialtyName(command.getSpecialtyName())
                .specialtyCode(command.getSpecialtyCode())
                .role(command.getRole())
                .businessId(command.getBusinessId())
                .createdAt(LocalDateTime.now())
                .createdBy(command.getCreatedBy())
                .build();

        UUID medicalTeamId = medicalTeamService.createMedicalTeam(medicalTeam);

        // Asignamos el ID generado al mensaje de respuesta
        command.setId(medicalTeamId);
    }
}