package com.kynsoft.cirugia.application.command.operatingroom.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteOperatingRoomCommand implements ICommand {

    private UUID id;
    private UUID operatingRoomId;

    public DeleteOperatingRoomCommand(UUID operatingRoomId) {
        this.id = UUID.randomUUID();
        this.operatingRoomId = operatingRoomId;
    }

    public static DeleteOperatingRoomCommand fromRequest(DeleteOperatingRoomRequest request) {
        return new DeleteOperatingRoomCommand(request.getOperatingRoomId());
    }

    @Override
    public ICommandMessage getMessage() {
        return new DeleteOperatingRoomMessage(id);
    }
}