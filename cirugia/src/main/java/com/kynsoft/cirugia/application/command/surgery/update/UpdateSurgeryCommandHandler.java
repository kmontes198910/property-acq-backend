package com.kynsoft.cirugia.application.command.surgery.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.ISurgeryService;
import com.kynsoft.cirugia.domain.dto.Surgery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateSurgeryCommandHandler implements ICommandHandler<UpdateSurgeryCommand> {

    private final ISurgeryService surgeryService;

    @Override
    @Transactional
    public void handle(UpdateSurgeryCommand command) {
        log.info("Updating surgery with ID: {}", command.getSurgeryId());
        
        Surgery surgery = Surgery.builder()
                .id(command.getSurgeryId())
                .patientId(command.getPatientId())
                .doctorId(command.getDoctorId())
                .specialtyId(command.getSpecialtyId())
                .surgeryType(command.getSurgeryType())
                .description(command.getDescription())
                .scheduledDate(command.getScheduledDate())
                .estimatedDurationMinutes(command.getEstimatedDurationMinutes())
                .complexityLevel(command.getComplexityLevel())
                .roomId(command.getRoomId())
                .requiresHospitalization(command.getRequiresHospitalization())
                .admissionReason(command.getAdmissionReason())
                .currentIllnessHistory(command.getCurrentIllnessHistory())
                .physicalExamination(command.getPhysicalExamination())
                .updatedBy(command.getUpdatedBy())
                .build();
        
        surgeryService.updateSurgery(surgery);
        

    }
}