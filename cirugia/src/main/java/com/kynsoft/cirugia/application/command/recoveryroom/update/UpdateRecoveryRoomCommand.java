package com.kynsoft.cirugia.application.command.recoveryroom.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateRecoveryRoomCommand implements ICommand {
    private UUID id;
    private UUID recoveryRoomId;
    private String name;
    private String description;
    private String floor;
    private String wing;
    private Integer capacity;
    private String status;
    private String roomType;
    private String additionalInfo;
    private UUID updatedBy;
    
    public UpdateRecoveryRoomCommand() {
        this.id = UUID.randomUUID();
    }
    
    public static UpdateRecoveryRoomCommand fromRequest(UpdateRecoveryRoomRequest request, UUID roomId, UUID updatedBy) {
        UpdateRecoveryRoomCommand command = new UpdateRecoveryRoomCommand();
        command.setRecoveryRoomId(roomId);
        command.setName(request.getName());
        command.setDescription(request.getDescription());
        command.setFloor(request.getFloor());
        command.setWing(request.getWing());
        command.setCapacity(request.getCapacity());
        command.setStatus(request.getStatus());
        command.setRoomType(request.getRoomType());
        command.setAdditionalInfo(request.getAdditionalInfo());
        command.setUpdatedBy(updatedBy);
        return command;
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new UpdateRecoveryRoomMessage(recoveryRoomId);
    }
}