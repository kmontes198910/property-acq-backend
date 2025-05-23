package com.kynsoft.medicaltest.application.command.labTestRequest.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.dto.LabTestItemRequestDto;
import com.kynsoft.medicaltest.domain.dto.LabTestRequestDto;
import com.kynsoft.medicaltest.domain.service.ILabTestRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class CreateLabTestRequestCommandHandler implements ICommandHandler<CreateLabTestRequestCommand> {

    private final ILabTestRequestService labTestRequestService;

    public CreateLabTestRequestCommandHandler(ILabTestRequestService labTestRequestService) {
        this.labTestRequestService = labTestRequestService;
    }

    @Override
    public void handle(CreateLabTestRequestCommand command) {
        log.info("Creando nuevo examen de laboratorio: {}", command.getId());

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
                        .completionDate(x.getCompletionDate())
                        .observations(x.getObservations())
                        .createdBy(command.getCreatedBy())
                        .build()
                );
            });
        }
        return values;
    }

}
