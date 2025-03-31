package com.kynsof.hospitalizationService.application.command.medicalStaff.update;

import com.kynsof.hospitalizationService.domain.dto.MedicalStaffDto;
import com.kynsof.hospitalizationService.domain.service.IMedicalStaffService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateMedicalStaffCommandHandler implements ICommandHandler<UpdateMedicalStaffCommand> {

    private final IMedicalStaffService service;

    public UpdateMedicalStaffCommandHandler(IMedicalStaffService service) {
        this.service = service;
    }

    @Override
    public void handle(UpdateMedicalStaffCommand command) {
        service.create(new MedicalStaffDto(
                command.getId(), 
                command.getFirstName(), 
                command.getLastName(), 
                command.getSpecialty(), 
                command.getLicenseNumber()
        ));
    }
}
