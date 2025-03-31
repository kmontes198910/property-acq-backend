package com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.create;

import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.dto.ResponsibleMedicalStaffDto;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.hospitalizationService.domain.service.IResponsibleMedicalStaffService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class CreateResponsibleMedicalStaffCommandHandler implements ICommandHandler<CreateResponsibleMedicalStaffCommand> {

    private final IEmergencyCaseService emergencyCaseService;
    private final IResponsibleMedicalStaffService responsibleMedicalStaffService;

    public CreateResponsibleMedicalStaffCommandHandler(IEmergencyCaseService emergencyCaseService,
                                         IResponsibleMedicalStaffService responsibleMedicalStaffService) {
        this.emergencyCaseService = emergencyCaseService;
        this.responsibleMedicalStaffService = responsibleMedicalStaffService;
    }

    @Override
    public void handle(CreateResponsibleMedicalStaffCommand command) {
        EmergencyCaseDto emergencyCaseDto = this.emergencyCaseService.findById(command.getEmergencyCase());
        responsibleMedicalStaffService.create(new ResponsibleMedicalStaffDto(
                command.getId(), 
                emergencyCaseDto, 
                command.getFirstName(), 
                command.getLastName(), 
                command.getIdentificationNumber(), 
                LocalDate.parse(command.getDateOfRecord()), 
                command.getSignature(), 
                command.getSeal()
        ));
    }
}
