package com.kynsof.patients.application.command.patientInsurance.create;

import com.kynsof.patients.domain.dto.PatientInsuranceDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.domain.service.IPatientInsuranceService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreatePatientInsuranceCommandHandler implements ICommandHandler<CreatePatientInsuranceCommand> {

    private final IPatientInsuranceService patientInsuranceService;

    public CreatePatientInsuranceCommandHandler(IPatientInsuranceService patientInsuranceService) {

        this.patientInsuranceService = patientInsuranceService;

    }

    @Override
    public void handle(CreatePatientInsuranceCommand command) {
        PatientInsuranceDto patientInsuranceDto = new PatientInsuranceDto(UUID.fromString(command.getPatientId()),
                command.getInsuranceId(), Status.ACTIVE);
        UUID id = patientInsuranceService.create(patientInsuranceDto);
        command.setId(id);
    }
}
