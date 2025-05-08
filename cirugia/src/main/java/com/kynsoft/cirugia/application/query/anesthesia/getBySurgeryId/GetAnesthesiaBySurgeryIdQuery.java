package com.kynsoft.cirugia.application.query.anesthesia.getBySurgeryId;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Consulta para obtener la anestesia asociada a una cirugía específica.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAnesthesiaBySurgeryIdQuery implements IQuery {
    private UUID surgeryId;
}