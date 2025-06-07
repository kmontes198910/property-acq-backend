package com.kynsoft.settings.application.command.recoveryroom.create;


import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateRecoveryRoomCommand implements ICommand {
    private UUID id;
    private String name;
    private String description;
    private String floor;
    private String wing;
    private Integer capacity;
    private String status;
    private UUID businessId;
    private String roomType;
    private String additionalInfo;
    private UUID createdBy;

    public CreateRecoveryRoomCommand() {
        this.id = UUID.randomUUID();
    }

    public CreateRecoveryRoomCommand(String name, String description, String floor, String wing,
                                     Integer capacity, String status, UUID businessId,
                                     String roomType, String additionalInfo, UUID createdBy) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.floor = floor;
        this.wing = wing;
        this.capacity = capacity;
        this.status = status;
        this.businessId = businessId;
        this.roomType = roomType;
        this.additionalInfo = additionalInfo;
        this.createdBy = createdBy;
    }

    public static CreateRecoveryRoomCommand fromRequest(CreateRecoveryRoomRequest request, UUID createdBy) {
        return new CreateRecoveryRoomCommand(
                request.getName(),
                request.getDescription(),
                request.getFloor(),
                request.getWing(),
                request.getCapacity(),
                request.getStatus(),
                request.getBusinessId(),
                request.getRoomType(),
                request.getAdditionalInfo(),
                createdBy
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateRecoveryRoomMessage(id);
    }
}