package com.kynsof.hospitalizationService.application.command.medicalStaff.create;

import com.kynsof.hospitalizationService.domain.dto.MedicalStaffDto;
import com.kynsof.hospitalizationService.domain.service.IMedicalStaffService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateMedicalStaffCommandHandler implements ICommandHandler<CreateMedicalStaffCommand> {

    private final IMedicalStaffService service;

    public CreateMedicalStaffCommandHandler(IMedicalStaffService service) {
        this.service = service;
    }

    @Override
    public void handle(CreateMedicalStaffCommand command) {
        service.create(new MedicalStaffDto(
                command.getId(), 
                command.getFirstName(), 
                command.getLastName(), 
                command.getSpecialty(), 
                command.getLicenseNumber()
        ));
    }
}
