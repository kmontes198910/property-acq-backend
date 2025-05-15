package com.kynsoft.cirugia.application.command.bedassignment.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferBedAssignmentRequest {
    private UUID id;
    private UUID bedId;
    private UUID roomId;

}
