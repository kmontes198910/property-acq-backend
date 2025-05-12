package com.kynsoft.cirugia.application.command.admision.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request for creating an admission
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdmisionRequest {
    private UUID surgeryId;
    private UUID roomId;
    private UUID bedId;
    private String observations;
}
