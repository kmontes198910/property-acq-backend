package com.kynsoft.cirugia.application.command.diagnosis.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDiagnosisCommand implements ICommand {
    private UUID id;
    private String icdCode;
    private String description;
    private String type;
    private UUID surgeryId;
    private UUID updatedBy;
    
    public UpdateDiagnosisCommand(UUID id, String icdCode, String description, String type, UUID surgeryId, UUID updatedBy) {
        this.id = id;
        this.icdCode = icdCode;
        this.description = description;
        this.type = type;
        this.surgeryId = surgeryId;
        this.updatedBy = updatedBy;
    }
    
    public static UpdateDiagnosisCommand fromRequest(UpdateDiagnosisRequest request, UUID id) {
        return new UpdateDiagnosisCommand(
                id,
                request.getIcdCode(),
                request.getDescription(),
                request.getType(),
                request.getSurgeryId(),
                request.getUpdatedBy()
        );
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new UpdateDiagnosisMessage(id);
    }
}