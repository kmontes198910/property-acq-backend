package com.kynsoft.cirugia.application.query.bedassignment;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.BedAssignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Resultado de la consulta para encontrar la última asignación activa de una cama
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindLastActiveBedAssignmentByBedIdQueryResult implements IResponse {
    private BedAssignment bedAssignment;
}
