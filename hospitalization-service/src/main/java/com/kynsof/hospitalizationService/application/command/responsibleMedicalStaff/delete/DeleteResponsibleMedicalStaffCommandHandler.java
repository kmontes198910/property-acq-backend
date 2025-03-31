package com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.delete;

import com.kynsof.hospitalizationService.domain.service.IResponsibleMedicalStaffService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteResponsibleMedicalStaffCommandHandler implements ICommandHandler<DeleteResponsibleMedicalStaffCommand> {

    private final IResponsibleMedicalStaffService serviceImpl;

    public DeleteResponsibleMedicalStaffCommandHandler(IResponsibleMedicalStaffService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteResponsibleMedicalStaffCommand command) {

        serviceImpl.delete(command.getId());
    }

}
