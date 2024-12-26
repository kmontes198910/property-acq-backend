package com.kynsof.patients.application.command.patientInsurance.update;

import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdatePatientInsuranceCommand implements ICommand {
    private UUID id;
    private final UUID insuranceId;
    private final Status status;

    public UpdatePatientInsuranceCommand(UUID id, UUID insuranceId, Status status) {
        this.insuranceId = insuranceId;
        this.status = status;
        this.id = id;
    }


    public static UpdatePatientInsuranceCommand fromRequest(UUID id, UpdatePatientInsuranceRequest request) {
        return new UpdatePatientInsuranceCommand(id, request.getInsuranceId(), request.getStatus());
    }


    @Override
    public ICommandMessage getMessage() {
        return new UpdatePatientInsuranceMessage();
    }
}
