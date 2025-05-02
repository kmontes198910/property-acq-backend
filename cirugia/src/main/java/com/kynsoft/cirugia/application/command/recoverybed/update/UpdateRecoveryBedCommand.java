package com.kynsoft.cirugia.application.command.recoverybed.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.cirugia.application.query.recoverybed.getbyid.RecoveryBedResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UpdateRecoveryBedCommand implements ICommand {
    private UUID id;
    private String bedNumber;
    private String location;
    private String type;
    private String status;
    private UUID businessId;
    private String floor;
    private String room;
    private Boolean hasMonitor;
    private Boolean hasOxygenSupply;
    private LocalDateTime lastMaintenanceDate;
    private UUID updatedBy;
    
    public static UpdateRecoveryBedCommand fromRequest(UpdateRecoveryBedRequest request, UUID id) {
        UpdateRecoveryBedCommand command = new UpdateRecoveryBedCommand();
        command.setId(id);
        command.setBedNumber(request.getBedNumber());
        command.setLocation(request.getLocation());
        command.setType(request.getType());
        command.setStatus(request.getStatus());
        command.setBusinessId(request.getBusinessId());
        command.setFloor(request.getFloor());
        command.setRoom(request.getRoom());
        command.setHasMonitor(request.getHasMonitor());
        command.setHasOxygenSupply(request.getHasOxygenSupply());
        command.setLastMaintenanceDate(request.getLastMaintenanceDate());
        command.setUpdatedBy(request.getUpdatedBy());
        return command;
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new UpdateRecoveryBedMessage(id);
    }
}