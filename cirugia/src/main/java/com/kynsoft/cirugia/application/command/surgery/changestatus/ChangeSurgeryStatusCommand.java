package com.kynsoft.cirugia.application.command.surgery.changestatus;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.cirugia.domain.enums.SurgeryStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChangeSurgeryStatusCommand implements ICommand {

    private UUID id;
    private UUID surgeryId;
    private SurgeryStatus status;
    private UUID updatedBy;

    public ChangeSurgeryStatusCommand(UUID surgeryId, SurgeryStatus status, UUID updatedBy) {
        this.id = UUID.randomUUID();
        this.surgeryId = surgeryId;
        this.status = status;
        this.updatedBy = updatedBy;
    }

    public static ChangeSurgeryStatusCommand fromRequest(ChangeSurgeryStatusRequest request) {
        return new ChangeSurgeryStatusCommand(
                request.getSurgeryId(),
                request.getStatus(),
                request.getUpdatedBy()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new ChangeSurgeryStatusMessage(id);
    }
}