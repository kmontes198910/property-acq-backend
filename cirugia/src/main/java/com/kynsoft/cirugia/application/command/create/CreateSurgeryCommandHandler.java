package com.kynsoft.cirugia.application.command.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.ISurgeryService;
import com.kynsoft.cirugia.domain.dto.Surgery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateSurgeryCommandHandler implements ICommandHandler<CreateSurgeryCommand> {

    private final ISurgeryService surgeryService;

    @Override
    @Transactional
    public void handle(CreateSurgeryCommand command) {
        log.info("Creating new surgery for patient: {}", command.getPatientId());

        Surgery surgery = Surgery.builder()
                .patientId(command.getPatientId())
                .doctorId(command.getDoctorId())
                .specialtyId(command.getSpecialtyId())
                .surgeryType(command.getSurgeryType())
                .description(command.getDescription())
                .scheduledDate(command.getScheduledDate())
                .estimatedDurationMinutes(command.getEstimatedDurationMinutes())
                .status("SCHEDULED") // Estado predeterminado para una nueva cirugía
                .complexityLevel(command.getComplexityLevel())
                .roomId(command.getRoomId())
                .requiresHospitalization(command.getRequiresHospitalization())
                .admissionReason(command.getAdmissionReason())
                .currentIllnessHistory(command.getCurrentIllnessHistory())
                .physicalExamination(command.getPhysicalExamination())
                .businessId(command.getBusinessId())
                .createdBy(command.getCreatedBy())
                .build();

        UUID surgeryId = surgeryService.createSurgery(surgery);

        // Asignamos el ID generado al mensaje de respuesta
        command.setId(surgeryId);
    }
}