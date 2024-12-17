package com.kynsof.treatments.application.command.treatment.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.ExternalConsultationDto;
import com.kynsof.treatments.domain.dto.MedicinesDto;
import com.kynsof.treatments.domain.dto.TreatmentDto;
import com.kynsof.treatments.domain.service.IExternalConsultationService;
import com.kynsof.treatments.domain.service.IMedicinesService;
import com.kynsof.treatments.domain.service.ITreatmentService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateTreatmentCommandHandler implements ICommandHandler<CreateTreatmentCommand> {

    private final ITreatmentService serviceImpl;
    private final IExternalConsultationService externalConsultationService;
    private final IMedicinesService medicinesService;

    public CreateTreatmentCommandHandler(ITreatmentService serviceImpl, IExternalConsultationService externalConsultationService, IMedicinesService medicinesService) {
        this.serviceImpl = serviceImpl;
        this.externalConsultationService = externalConsultationService;
        this.medicinesService = medicinesService;
    }

    @Override
    public void handle(CreateTreatmentCommand command) {
        ExternalConsultationDto externalConsultationDto = this.externalConsultationService.findById(UUID.fromString(command.getExternalConsultId()));
        MedicinesDto medicinesDto = this.medicinesService.findById(UUID.fromString(command.getMedication()));
        UUID id = UUID.randomUUID();
        TreatmentDto create = new TreatmentDto(
                id,
                command.getDescription(),
                medicinesDto,
                command.getQuantity(),
                command.getMedicineUnit()
        );
        create.setExternalConsultationDto(externalConsultationDto);
        serviceImpl.create(create);
        command.setId(id);
    }
}
