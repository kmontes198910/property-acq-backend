package com.kynsoft.cirugia.application.command.surgery.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.IDoctorService;
import com.kynsoft.cirugia.domain.service.IPatientsService;
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
    private final IDoctorService doctorService;
    private final IPatientsService patientsService;

    @Override
    @Transactional
    public void handle(CreateSurgeryCommand command) {
        log.info("Creating new surgery for patient: {}", command.getPatientId());
        doctorService.findById(command.getDoctorId());
        patientsService.findById(command.getPatientId());

        Surgery surgery = Surgery.builder()
                .patientId(command.getPatientId())
                .doctorId(command.getDoctorId())
                .specialtyId(command.getSpecialtyId())
                .operatingRoomId(command.getOperatingRoomId())
                .surgeryType(command.getSurgeryType())
                .scheduledDate(command.getScheduledDate())
                .startTime(command.getStartTime())
                .endingTime(command.getEndingTime())
                .requiresHospitalization(command.getRequiresHospitalization())
                .status("SCHEDULED") // Estado predeterminado para una nueva cirugía
                .businessId(command.getBusinessId())
                .createdBy(command.getCreatedBy())
                .build();

        UUID surgeryId = surgeryService.createSurgery(surgery);

        // Asignamos el ID generado al mensaje de respuesta
        command.setId(surgeryId);
    }
}