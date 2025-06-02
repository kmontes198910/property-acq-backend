package com.kynsoft.cirugia.application.query.bedassignment;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.BedAssignment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Respuesta para la consulta de asignaciones de cama por ID de cirugía.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindBedAssignmentsBySurgeryQueryResult implements IResponse {
    private List<BedAssignment> bedAssignments;
}
