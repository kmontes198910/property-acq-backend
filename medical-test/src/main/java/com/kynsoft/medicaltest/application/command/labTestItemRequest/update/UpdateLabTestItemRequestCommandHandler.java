package com.kynsoft.medicaltest.application.command.labTestItemRequest.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.dto.LabTestItemRequestDto;
import com.kynsoft.medicaltest.domain.dto.LabTestRequestDto;
import com.kynsoft.medicaltest.domain.service.ILabTestItemRequestService;
import com.kynsoft.medicaltest.domain.service.ILabTestRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateLabTestItemRequestCommandHandler implements ICommandHandler<UpdateLabTestItemRequestCommand> {

    private final ILabTestRequestService labTestRequestService;
    private final ILabTestItemRequestService labTestItemRequestService;

    public UpdateLabTestItemRequestCommandHandler(ILabTestRequestService labTestRequestService, ILabTestItemRequestService labTestItemRequestService) {
        this.labTestRequestService = labTestRequestService;
        this.labTestItemRequestService = labTestItemRequestService;
    }

    @Override
    public void handle(UpdateLabTestItemRequestCommand command) {
        log.info("Creando nuevo examen de laboratorio: {}", command.getId());
        LabTestRequestDto labTestRequestDto = this.labTestRequestService.findById(command.getOrder());

        // Crear DTO a partir del comando
        LabTestItemRequestDto dto = LabTestItemRequestDto.builder()
                .id(command.getId())
                .status(command.getStatus())
                .observations(command.getObservations())
                .updatedBy(command.getUpdatedBy())
                .code(command.getCode())
                .order(labTestRequestDto)
                .completionDate(command.getCompletionDate())
                .description(command.getExaminationType())
                .build();

        labTestItemRequestService.update(dto);
    }

}
