package com.kynsoft.cirugia.application.command.recoverybed.changestatus;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChangeRecoveryBedStatusRequest {
    private UUID id;
    private UUID recoveryBedId;
    private String status;
    private UUID updatedBy;
}