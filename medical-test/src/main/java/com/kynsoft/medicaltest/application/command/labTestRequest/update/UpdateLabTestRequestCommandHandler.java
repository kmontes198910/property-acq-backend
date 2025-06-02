package com.kynsoft.medicaltest.application.command.labTestRequest.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.dto.LabTestRequestDto;
import com.kynsoft.medicaltest.domain.dto.PatientDto;
import com.kynsoft.medicaltest.domain.service.ILabTestRequestService;
import com.kynsoft.medicaltest.domain.service.IPatientsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateLabTestRequestCommandHandler implements ICommandHandler<UpdateLabTestRequestCommand> {

    private final ILabTestRequestService labTestRequestService;
    private final IPatientsService patientsService;

    public UpdateLabTestRequestCommandHandler(ILabTestRequestService labTestRequestService, IPatientsService patientsService) {
        this.labTestRequestService = labTestRequestService;
        this.patientsService = patientsService;
    }

    @Override
    public void handle(UpdateLabTestRequestCommand command) {
        log.info("Creando nuevo examen de laboratorio: {}", command.getId());
        PatientDto patient = this.patientsService.findById(command.getPatientId());

        // Crear DTO a partir del comando
        LabTestRequestDto dto = LabTestRequestDto.builder()
                .id(command.getId())
                .patientId(command.getPatientId())
                .doctorId(command.getDoctorId())
                .creationDate(command.getCreationDate())
                .status(command.getStatus())
                .observations(command.getObservations())
                .businessId(command.getBusinessId())
                .isActive(command.isActive())
                .updatedBy(command.getUpdateBy())
                .patient(patient)
                .build();
        labTestRequestService.update(dto);
    }

}
