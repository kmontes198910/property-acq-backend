package com.kynsoft.cirugia.application.command.operatingroom.changestatus;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChangeOperatingRoomStatusCommand implements ICommand {

    private UUID id;
    private UUID operatingRoomId;
    private String status;
    private UUID updatedBy;

    public ChangeOperatingRoomStatusCommand(UUID operatingRoomId, String status, UUID updatedBy) {
        this.id = UUID.randomUUID();
        this.operatingRoomId = operatingRoomId;
        this.status = status;
        this.updatedBy = updatedBy;
    }

    public static ChangeOperatingRoomStatusCommand fromRequest(ChangeOperatingRoomStatusRequest request) {
        return new ChangeOperatingRoomStatusCommand(
                request.getOperatingRoomId(),
                request.getStatus().toString(),
                request.getUpdatedBy()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new ChangeOperatingRoomStatusMessage(id);
    }
}