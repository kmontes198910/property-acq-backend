package com.kynsoft.cirugia.application.command.changestatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeSurgeryStatusRequest {
    private UUID surgeryId;
    private String status;
    private UUID updatedBy;
}