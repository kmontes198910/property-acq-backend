package com.kynsoft.settings.application.command.recoveryroom.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecoveryRoomRequest {
    private String name;
    private String description;
    private String floor;
    private String wing;
    private Integer capacity;
    private String status;
    private String roomType;
    private String additionalInfo;
}