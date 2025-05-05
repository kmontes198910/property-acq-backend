package com.kynsoft.cirugia.application.command.diagnosis.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;

import java.util.UUID;

@Getter
@Setter
public class CreateDiagnosisCommand implements ICommand {
    private UUID id;
    private String icdCode;
    private String description;
    private String type;
    private UUID surgeryId;
    private UUID createdBy;
    
    public CreateDiagnosisCommand() {
        this.id = UUID.randomUUID();
    }
    
    public CreateDiagnosisCommand(String icdCode, String description, String type, UUID surgeryId, UUID createdBy) {
        this.id = UUID.randomUUID();
        this.icdCode = icdCode;
        this.description = description;
        this.type = type;
        this.surgeryId = surgeryId;
        this.createdBy = createdBy;
    }
    
    public static CreateDiagnosisCommand fromRequest(CreateDiagnosisRequest request, UUID createdBy) {
        return new CreateDiagnosisCommand(
                request.getIcdCode(),
                request.getDescription(),
                request.getType(),
                request.getSurgeryId(),
                createdBy
        );
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new CreateDiagnosisMessage(id);
    }
}