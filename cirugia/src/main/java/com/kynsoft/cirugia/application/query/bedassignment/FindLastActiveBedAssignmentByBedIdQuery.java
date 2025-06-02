package com.kynsoft.cirugia.application.query.bedassignment;


import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * Consulta para encontrar la última asignación activa para una cama específica
 */
@Getter
@AllArgsConstructor
public class FindLastActiveBedAssignmentByBedIdQuery implements IQuery {
    private final UUID bedId;
    private final UUID businessId;
}
