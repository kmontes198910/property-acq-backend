package com.kynsof.hospitalizationService.application.command.medicalPrescription.delete;

import com.kynsof.hospitalizationService.domain.service.IMedicalPrescriptionService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteMedicalPrescriptionCommandHandler implements ICommandHandler<DeleteMedicalPrescriptionCommand> {

    private final IMedicalPrescriptionService serviceImpl;

    public DeleteMedicalPrescriptionCommandHandler(IMedicalPrescriptionService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteMedicalPrescriptionCommand command) {

        serviceImpl.delete(command.getId());
    }

}
