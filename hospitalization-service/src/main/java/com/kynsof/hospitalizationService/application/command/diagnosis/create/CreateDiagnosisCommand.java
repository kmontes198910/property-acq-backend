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
    private String code;

    public CreateDiagnosisCommand(UUID emergencyCase, String diagnosisType, String diagnosisDescription, String code) {
        this.id = UUID.randomUUID();
        this.emergencyCase = emergencyCase;
        this.diagnosisType = diagnosisType;
        this.diagnosisDescription = diagnosisDescription;
        this.code = code;
    }

    public static CreateDiagnosisCommand fromRequest(CreateDiagnosisRequest request) {
        return new CreateDiagnosisCommand(
                request.getEmergencyCase(),
                request.getDiagnosisType(),
                request.getDiagnosisDescription(),
                request.getCode()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateDiagnosisMessage(id);
    }
}
