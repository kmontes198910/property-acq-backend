package com.kynsoft.cirugia.application.command.surgery.changestatus;

import com.kynsoft.cirugia.domain.enums.SurgeryStatus;
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
    private SurgeryStatus status;
    private UUID updatedBy;
}