package com.kynsoft.cirugia.application.command.surgery.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.IDoctorService;
import com.kynsoft.cirugia.domain.service.IPatientsService;
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
    private final IDoctorService doctorService;
    private final IPatientsService patientsService;

    @Override
    @Transactional
    public void handle(UpdateSurgeryCommand command) {
        log.info("Updating surgery with ID: {}", command.getSurgeryId());
        doctorService.findById(command.getDoctorId());
        patientsService.findById(command.getPatientId());
        Surgery surgery = Surgery.builder()
                .id(command.getSurgeryId())
                .patientId(command.getPatientId())
                .doctorId(command.getDoctorId())
                .specialtyId(command.getSpecialtyId())
                .surgeryType(command.getSurgeryType())
                .scheduledDate(command.getScheduledDate())
                .startTime(command.getStartTime())
                .endingTime(command.getEndingTime())
                .requiresHospitalization(command.getRequiresHospitalization())
                .updatedBy(command.getUpdatedBy())
                .build();
        
        surgeryService.updateSurgery(surgery);
    }
}