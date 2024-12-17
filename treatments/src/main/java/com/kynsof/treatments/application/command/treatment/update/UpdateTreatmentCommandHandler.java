package com.kynsof.treatments.application.command.treatment.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.MedicinesDto;
import com.kynsof.treatments.domain.dto.TreatmentDto;
import com.kynsof.treatments.domain.service.IMedicinesService;
import com.kynsof.treatments.domain.service.ITreatmentService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateTreatmentCommandHandler implements ICommandHandler<UpdateTreatmentCommand> {

    private final ITreatmentService serviceImpl;
    private final IMedicinesService medicinesService;

    public UpdateTreatmentCommandHandler(ITreatmentService serviceImpl,  IMedicinesService medicinesService) {
        this.serviceImpl = serviceImpl;
        this.medicinesService = medicinesService;
    }

    @Override
    public void handle(UpdateTreatmentCommand command) {
        TreatmentDto treatmentDto = this.serviceImpl.findById(command.getTreatmentId());
        treatmentDto.setDescription(command.getDescription());
        MedicinesDto medicinesDto = this.medicinesService.findById(UUID.fromString(command.getMedication()));
        treatmentDto.setMedication(medicinesDto);
        treatmentDto.setQuantity(command.getQuantity());
        treatmentDto.setMedicineUnit(command.getMedicineUnit());
        this.serviceImpl.update(treatmentDto);

    }
}
