package com.kynsoft.cirugia.application.query.admision.getbysurgeryid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Query para obtener una admisión por su ID de cirugía
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAdmisionBySurgeryIdQuery implements IQuery {
    private UUID surgeryId;
}
