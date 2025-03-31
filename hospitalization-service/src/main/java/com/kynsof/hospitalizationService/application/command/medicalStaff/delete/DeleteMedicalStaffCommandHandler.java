package com.kynsof.hospitalizationService.application.command.medicalStaff.delete;

import com.kynsof.hospitalizationService.domain.service.IMedicalStaffService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteMedicalStaffCommandHandler implements ICommandHandler<DeleteMedicalStaffCommand> {

    private final IMedicalStaffService serviceImpl;

    public DeleteMedicalStaffCommandHandler(IMedicalStaffService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteMedicalStaffCommand command) {

        serviceImpl.delete(command.getId());
    }

}
