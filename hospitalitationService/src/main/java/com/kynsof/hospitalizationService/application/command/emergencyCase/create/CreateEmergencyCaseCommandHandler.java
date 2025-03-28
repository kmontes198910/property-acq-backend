package com.kynsof.hospitalizationService.application.command.emergencyCase.create;

import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.dto.PatientDto;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.hospitalizationService.domain.service.IPatientsService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
public class CreateEmergencyCaseCommandHandler implements ICommandHandler<CreateEmergencyCaseCommand> {

    private final IEmergencyCaseService emergencyCaseService;
    private final IPatientsService patientsService;

    public CreateEmergencyCaseCommandHandler(IEmergencyCaseService emergencyCaseService,
                                          IPatientsService patientsService) {
        this.emergencyCaseService = emergencyCaseService;
        this.patientsService = patientsService;
    }

    @Override
    public void handle(CreateEmergencyCaseCommand command) {
        PatientDto patientDto = this.patientsService.findById(command.getPatient());
        emergencyCaseService.create(new EmergencyCaseDto(
                command.getId(),
                patientDto,
                LocalDate.parse(command.getAdmissionDate()),
                LocalTime.parse(command.getAdmissionTime()),
                command.getAdmissionType(),
                command.getStatus()
        ));
    }
}
