package com.kynsof.hospitalizationService.application.command.prescribedMedication.delete;

import com.kynsof.hospitalizationService.domain.service.IPrescribedMedicationService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeletePrescribedMedicationCommandHandler implements ICommandHandler<DeletePrescribedMedicationCommand> {

    private final IPrescribedMedicationService serviceImpl;

    public DeletePrescribedMedicationCommandHandler(IPrescribedMedicationService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeletePrescribedMedicationCommand command) {

        serviceImpl.delete(command.getId());
    }

}
