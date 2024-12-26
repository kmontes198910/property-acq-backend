package com.kynsof.patients.application.command.patientInsurance.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatePatientInsuranceCommand implements ICommand {
    private UUID id;
    private final String patientId;
    private final UUID insuranceId;

    public CreatePatientInsuranceCommand(String patientId, UUID insuranceId) {
        this.patientId = patientId;
        this.insuranceId = insuranceId;
    }


    public static CreatePatientInsuranceCommand fromRequest(CreatePatientInsuranceRequest request) {
        return new CreatePatientInsuranceCommand(request.getPatientId(), request.getInsuranceId());
    }


    @Override
    public ICommandMessage getMessage() {
        return new CreatePatientInsuranceMessage(id);
    }
}
