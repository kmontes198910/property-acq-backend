package com.kynsoft.cirugia.application.query.admision.getbysurgeryid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Respuesta de la consulta de admisión por ID de cirugía
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmisionBySurgeryQueryResponse implements IResponse {
    private UUID id;
    private UUID sala;
    private String cama;
    private String observaciones;
    private UUID surgeryId;
    private UUID createdBy;
    private UUID updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
