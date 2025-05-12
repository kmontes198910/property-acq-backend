package com.kynsoft.cirugia.application.query.admision;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response for admission query
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdmisionResponse  implements IResponse {
    private UUID id;
    private UUID roomId;
    private UUID bedId;
    private String observations;
    private UUID surgeryId;
    private UUID createdBy;
    private UUID updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
