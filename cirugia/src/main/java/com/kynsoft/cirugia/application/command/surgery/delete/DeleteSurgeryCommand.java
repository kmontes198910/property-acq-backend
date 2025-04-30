package com.kynsoft.cirugia.application.command.surgery.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteSurgeryCommand implements ICommand {

    private UUID id;
    private UUID surgeryId;

    public DeleteSurgeryCommand(UUID surgeryId) {
        this.id = UUID.randomUUID();
        this.surgeryId = surgeryId;
    }

    public static DeleteSurgeryCommand fromRequest(DeleteSurgeryRequest request) {
        return new DeleteSurgeryCommand(request.getSurgeryId());
    }

    @Override
    public ICommandMessage getMessage() {
        return new DeleteSurgeryMessage(id);
    }
}