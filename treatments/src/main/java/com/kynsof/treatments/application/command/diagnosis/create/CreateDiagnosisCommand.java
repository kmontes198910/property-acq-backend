package com.kynsof.treatments.application.command.diagnosis.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateDiagnosisCommand implements ICommand {
    private UUID id;
    private final String icdCode; // Código CIE-10
    private final String description;
    private final UUID idExternalConsultation;

    public CreateDiagnosisCommand(String icdCode, String description, UUID idExternalConsultation) {
        this.icdCode = icdCode;
        this.description = description;
        this.idExternalConsultation = idExternalConsultation;
    }


    public static CreateDiagnosisCommand fromRequest(DiagnosisRequest request) {
        return new CreateDiagnosisCommand(request.getIcdCode(),
                request.getDescription(), request.getIdExternalConsultation());
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateDiagnosisMessage(id);
    }
}
