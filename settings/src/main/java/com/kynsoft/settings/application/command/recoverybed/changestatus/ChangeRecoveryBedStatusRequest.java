package com.kynsoft.settings.application.command.recoverybed.changestatus;

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