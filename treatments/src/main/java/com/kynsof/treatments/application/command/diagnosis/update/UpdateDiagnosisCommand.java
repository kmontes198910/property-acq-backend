package com.kynsof.treatments.application.command.diagnosis.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateDiagnosisCommand implements ICommand {
    private final UUID id;
    private final String icdCode; // Código CIE-10
    private final String description;
    private final  UUID idExternalConsultation;

    public UpdateDiagnosisCommand(UUID id, String icdCode, String description, UUID idExternalConsultation) {
        this.id = id;
        this.icdCode = icdCode;
        this.description = description;
        this.idExternalConsultation = idExternalConsultation;
    }


    public static UpdateDiagnosisCommand fromRequest(UUID id,UpdateDiagnosisRequest request) {
        return new UpdateDiagnosisCommand(id, request.getIcdCode(), request.getDescription(), request.getIdExternalConsultation());
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateDiagnosisMessage(true);
    }
}
