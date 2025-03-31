package com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.update;

import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.dto.ResponsibleMedicalStaffDto;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.hospitalizationService.domain.service.IResponsibleMedicalStaffService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class UpdateResponsibleMedicalStaffCommandHandler implements ICommandHandler<UpdateResponsibleMedicalStaffCommand> {

    private final IEmergencyCaseService emergencyCaseService;
    private final IResponsibleMedicalStaffService responsibleMedicalStaffService;

    public UpdateResponsibleMedicalStaffCommandHandler(IEmergencyCaseService emergencyCaseService,
                                         IResponsibleMedicalStaffService responsibleMedicalStaffService) {
        this.emergencyCaseService = emergencyCaseService;
        this.responsibleMedicalStaffService = responsibleMedicalStaffService;
    }

    @Override
    public void handle(UpdateResponsibleMedicalStaffCommand command) {
        EmergencyCaseDto emergencyCaseDto = this.emergencyCaseService.findById(command.getEmergencyCase());

        ResponsibleMedicalStaffDto update = this.responsibleMedicalStaffService.findById(command.getId());
        update.setDateOfRecord(LocalDate.parse(command.getDateOfRecord()));
        update.setEmergencyCase(emergencyCaseDto);
        update.setFirstName(command.getFirstName());
        update.setIdentificationNumber(command.getIdentificationNumber());
        update.setLastName(command.getLastName());
        update.setSeal(command.getSeal());
        update.setSignature(command.getSignature());

        responsibleMedicalStaffService.update(update);
    }
}
