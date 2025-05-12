package com.kynsoft.cirugia.application.command.admision.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request for updating an admission
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdmisionRequest {
    private UUID id;
    private UUID roomId;
    private UUID bedId;
    private String observations;
    private UUID updatedBy;
}
