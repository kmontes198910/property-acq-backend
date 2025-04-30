package com.kynsoft.cirugia.application.command.medicalteam.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteMedicalTeamCommand implements ICommand {
    private UUID id;

    public DeleteMedicalTeamCommand(UUID id) {
        this.id = id;
    }

    @Override
    public ICommandMessage getMessage() {
        return new DeleteMedicalTeamMessage(id);
    }
}