package com.kynsoft.cirugia.application.command.recoverybed.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CreateRecoveryBedCommand implements ICommand {
    private UUID id;
    private String bedNumber;
    private String location;
    private String type;
    private String status;
    private UUID businessId;
    private String floor;
    private Boolean hasMonitor;
    private Boolean hasOxygenSupply;
    private LocalDateTime lastMaintenanceDate;
    private UUID recoveryRoomId;
    private UUID createdBy;
    
    public CreateRecoveryBedCommand() {
        this.id = UUID.randomUUID();
    }
    
    public static CreateRecoveryBedCommand fromRequest(CreateRecoveryBedRequest request, String userId) {
        CreateRecoveryBedCommand command = new CreateRecoveryBedCommand();
        command.setBedNumber(request.getBedNumber());
        command.setLocation(request.getLocation());
        command.setType(request.getType());
        command.setStatus(request.getStatus());
        command.setBusinessId(request.getBusinessId());
        command.setFloor(request.getFloor());
        command.setHasMonitor(request.getHasMonitor());
        command.setHasOxygenSupply(request.getHasOxygenSupply());
        command.setLastMaintenanceDate(request.getLastMaintenanceDate());
        command.setRecoveryRoomId(request.getRecoveryRoomId());
        command.setCreatedBy(UUID.fromString(userId));
        return command;
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new CreateRecoveryBedMessage(id);
    }
}