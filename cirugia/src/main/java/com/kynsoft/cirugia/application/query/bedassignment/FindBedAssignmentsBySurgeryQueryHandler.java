package com.kynsoft.cirugia.application.query.bedassignment;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.dto.BedAssignment;
import com.kynsoft.cirugia.domain.service.IBedAssignmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Manejador para la consulta de asignaciones de cama por ID de cirugía.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FindBedAssignmentsBySurgeryQueryHandler implements IQueryHandler<FindBedAssignmentsBySurgeryQuery, FindBedAssignmentsBySurgeryQueryResult> {

    private final IBedAssignmentService bedAssignmentService;
    
    @Override
    public FindBedAssignmentsBySurgeryQueryResult handle(FindBedAssignmentsBySurgeryQuery query) {
        log.info("Buscando asignaciones de cama para cirugía con ID: {}", query.getSurgeryId());
        
        List<BedAssignment> assignments = bedAssignmentService.findBySurgeryId(query.getSurgeryId());
        log.info("Encontradas {} asignaciones para la cirugía", assignments.size());
        
        // Devolvemos un resultado incluso si la lista está vacía
        return new FindBedAssignmentsBySurgeryQueryResult(assignments);
    }
}
