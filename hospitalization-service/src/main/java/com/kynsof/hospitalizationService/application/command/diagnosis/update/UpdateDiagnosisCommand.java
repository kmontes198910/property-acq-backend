package com.kynsof.hospitalizationService.application.command.diagnosis.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateDiagnosisCommand implements ICommand {

    private UUID id;
    private UUID emergencyCase;
    private String diagnosisType;
    private String diagnosisDescription;
    private String code;

    public UpdateDiagnosisCommand(UUID id, UUID emergencyCase, String diagnosisType, String diagnosisDescription, String code) {
        this.id = id;
        this.emergencyCase = emergencyCase;
        this.diagnosisType = diagnosisType;
        this.diagnosisDescription = diagnosisDescription;
        this.code = code;
    }

    public static UpdateDiagnosisCommand fromRequest(UpdateDiagnosisRequest request, UUID id) {
        return new UpdateDiagnosisCommand(
                id,
                request.getEmergencyCase(),
                request.getDiagnosisType(),
                request.getDiagnosisDescription(),
                request.getCode()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateDiagnosisMessage(id);
    }
}
