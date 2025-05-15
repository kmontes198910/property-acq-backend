package com.kynsoft.cirugia.application.query.bedassignment;


import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.dto.BedAssignment;
import com.kynsoft.cirugia.domain.service.IBedAssignmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Manejador para encontrar la última asignación activa de una cama específica
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FindLastActiveBedAssignmentByBedIdQueryHandler 
    implements IQueryHandler<FindLastActiveBedAssignmentByBedIdQuery, FindLastActiveBedAssignmentByBedIdQueryResult> {
    
    private final IBedAssignmentRepository bedAssignmentRepository;
    
    @Override
    public FindLastActiveBedAssignmentByBedIdQueryResult handle(FindLastActiveBedAssignmentByBedIdQuery query) {
        log.info("Buscando última asignación activa para la cama con ID: {}", query.getBedId());
        
        BedAssignment bedAssignment = bedAssignmentRepository.findLastActiveAssignmentByBedId(
                query.getBedId(), query.getBusinessId());
        
        return FindLastActiveBedAssignmentByBedIdQueryResult.builder()
                .bedAssignment(bedAssignment)
                .build();
    }
}
