package com.kynsoft.medicaltest.application.command.labTestItemRequest.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.dto.LabTestItemRequestDto;
import com.kynsoft.medicaltest.domain.dto.LabTestRequestDto;
import com.kynsoft.medicaltest.domain.service.ILabTestItemRequestService;
import com.kynsoft.medicaltest.domain.service.ILabTestRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateLabTestItemRequestCommandHandler implements ICommandHandler<CreateLabTestItemRequestCommand> {

    private final ILabTestRequestService labTestRequestService;
    private final ILabTestItemRequestService labTestItemRequestService;

    public CreateLabTestItemRequestCommandHandler(ILabTestRequestService labTestRequestService, ILabTestItemRequestService labTestItemRequestService) {
        this.labTestRequestService = labTestRequestService;
        this.labTestItemRequestService = labTestItemRequestService;
    }

    @Override
    public void handle(CreateLabTestItemRequestCommand command) {
        log.info("Creando nuevo examen de laboratorio: {}", command.getId());
        LabTestRequestDto labTestRequestDto = this.labTestRequestService.findById(command.getOrder());

        // Crear DTO a partir del comando
        LabTestItemRequestDto dto = LabTestItemRequestDto.builder()
                .id(command.getId())
                .status(command.getStatus())
                .observations(command.getObservations())
                .createdBy(command.getCreatedBy())
                .code(command.getCode())
                .order(labTestRequestDto)
                .completionDate(command.getCompletionDate())
                .examinationType(command.getExaminationType())
                .build();

        labTestItemRequestService.create(dto);
    }

}
