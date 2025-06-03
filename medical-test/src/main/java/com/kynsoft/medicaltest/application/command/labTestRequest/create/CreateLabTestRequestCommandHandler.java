package com.kynsoft.medicaltest.application.command.labTestRequest.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.dto.DoctorDto;
import com.kynsoft.medicaltest.domain.dto.LabTestItemRequestDto;
import com.kynsoft.medicaltest.domain.dto.LabTestRequestDto;
import com.kynsoft.medicaltest.domain.dto.PatientDto;
import com.kynsoft.medicaltest.domain.service.IDoctorService;
import com.kynsoft.medicaltest.domain.service.ILabTestRequestService;
import com.kynsoft.medicaltest.domain.service.IPatientsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class CreateLabTestRequestCommandHandler implements ICommandHandler<CreateLabTestRequestCommand> {

    private final ILabTestRequestService labTestRequestService;
    private final IPatientsService patientsService;
    private final IDoctorService doctorService;

    public CreateLabTestRequestCommandHandler(ILabTestRequestService labTestRequestService, IPatientsService patientsService, IDoctorService doctorService) {
        this.labTestRequestService = labTestRequestService;
        this.patientsService = patientsService;
        this.doctorService = doctorService;
    }

    @Override
    public void handle(CreateLabTestRequestCommand command) {
        log.info("Creando nuevo examen de laboratorio: {}", command.getId());
        PatientDto patient = this.patientsService.findById(command.getPatientId());
        DoctorDto doctor = this.doctorService.findById(command.getDoctorId());
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
                .createdBy(command.getCreatedBy())
                .examinations(examinations(command))
                .patient(patient)
                .build();
        // Guardar a través del servicio
        labTestRequestService.create(dto);
    }

    private List<LabTestItemRequestDto> examinations(CreateLabTestRequestCommand command) {
        List<LabTestItemRequestDto> values = new ArrayList<>();
        if (command.getLabTestItems() != null) {
            command.getLabTestItems().forEach(x -> {
                values.add(LabTestItemRequestDto.builder()
                        .id(UUID.randomUUID())
                        .code(x.getCode())
                        .examinationType(x.getExaminationType())
                        .status(x.getStatus())
                        .observations(x.getObservations())
                        .createdBy(command.getCreatedBy())
                        .build()
                );
            });
        }
        return values;
    }

}
