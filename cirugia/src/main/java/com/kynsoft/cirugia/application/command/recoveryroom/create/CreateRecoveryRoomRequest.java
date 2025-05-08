package com.kynsoft.cirugia.application.command.recoveryroom.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRecoveryRoomRequest {
    private String name;
    private String description;
    private String floor;
    private String wing;
    private Integer capacity;
    private String status;
    private UUID businessId;
    private String roomType;
    private String additionalInfo;
}