package com.kynsof.hospitalizationService.application.command.emergencyCase.update;

import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.hospitalizationService.domain.service.IPatientsService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
public class UpdateEmergencyCaseCommandHandler implements ICommandHandler<UpdateEmergencyCaseCommand> {

    private final IEmergencyCaseService emergencyCaseService;
    private final IPatientsService patientsService;


    public UpdateEmergencyCaseCommandHandler(IEmergencyCaseService emergencyCaseService,
                                             IPatientsService patientsService) {
        this.emergencyCaseService = emergencyCaseService;
        this.patientsService = patientsService;
    }

    @Override
    public void handle(UpdateEmergencyCaseCommand command) {
        EmergencyCaseDto object = this.emergencyCaseService.findById(command.getId());

        object.setAdmissionDate(LocalDate.parse(command.getAdmissionDate()));
        object.setAdmissionTime(LocalTime.parse(command.getAdmissionTime()));
        object.setAdmissionType(command.getAdmissionType());
        object.setStatus(command.getStatus());
        emergencyCaseService.update(object);
    }
}
