package com.kynsoft.cirugia.application.command.treatment.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteTreatmentCommand implements ICommand {
    private UUID id;
    private UUID treatmentId;

    public DeleteTreatmentCommand(UUID treatmentId) {
        this.id = UUID.randomUUID();
        this.treatmentId = treatmentId;
    }

    @Override
    public ICommandMessage getMessage() {
        return new DeleteTreatmentMessage(id);
    }
}