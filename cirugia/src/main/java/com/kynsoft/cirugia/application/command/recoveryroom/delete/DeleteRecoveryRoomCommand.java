package com.kynsoft.cirugia.application.command.recoveryroom.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DeleteRecoveryRoomCommand implements ICommand {
    private UUID id;
    
    public DeleteRecoveryRoomCommand() {
        this.id = UUID.randomUUID();
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new DeleteRecoveryRoomMessage(id);
    }
}