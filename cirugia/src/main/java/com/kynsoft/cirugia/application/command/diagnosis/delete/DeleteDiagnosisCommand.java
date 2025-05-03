package com.kynsoft.cirugia.application.command.diagnosis.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteDiagnosisCommand implements ICommand {
    private UUID id;
    private UUID diagnosisId;
    
    public DeleteDiagnosisCommand(UUID diagnosisId) {
        this.id = UUID.randomUUID();
        this.diagnosisId = diagnosisId;
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new DeleteDiagnosisMessage(id);
    }
}