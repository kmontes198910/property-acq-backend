package com.kynsof.hospitalizationService.application.command.diagnosis.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateDiagnosisCommand implements ICommand {

    private UUID id;
    private UUID emergencyCase;
    private String diagnosisType;
    private String diagnosisDescription;

    public CreateDiagnosisCommand(UUID emergencyCase, String diagnosisType, String diagnosisDescription) {
        this.id = UUID.randomUUID();
        this.emergencyCase = emergencyCase;
        this.diagnosisType = diagnosisType;
        this.diagnosisDescription = diagnosisDescription;
    }

    public static CreateDiagnosisCommand fromRequest(CreateDiagnosisRequest request) {
        return new CreateDiagnosisCommand(
                request.getEmergencyCase(),
                request.getDiagnosisType(),
                request.getDiagnosisDescription()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateDiagnosisMessage(id);
    }
}
