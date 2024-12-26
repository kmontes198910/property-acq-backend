package com.kynsof.patients.application.command.patientInsurance.update;

import com.kynsof.patients.domain.dto.AllergyEntityDto;
import com.kynsof.patients.domain.dto.PatientInsuranceDto;
import com.kynsof.patients.domain.service.IAllergyService;
import com.kynsof.patients.domain.service.IPatientInsuranceService;
import com.kynsof.patients.infrastructure.entity.Allergy;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdatePatientInsuranceCommandHandler implements ICommandHandler<UpdatePatientInsuranceCommand> {

    private final IPatientInsuranceService allergyService;

    public UpdatePatientInsuranceCommandHandler(IPatientInsuranceService allergyService) {
        this.allergyService = allergyService;
    }

    @Override
    public void handle(UpdatePatientInsuranceCommand command) {
        PatientInsuranceDto patientInsuranceDto = new PatientInsuranceDto();
        patientInsuranceDto.setId(command.getId());
        patientInsuranceDto.setInsuranceId(command.getInsuranceId());
        patientInsuranceDto.setStatus(command.getStatus());
        allergyService.update(patientInsuranceDto);
    }
}
