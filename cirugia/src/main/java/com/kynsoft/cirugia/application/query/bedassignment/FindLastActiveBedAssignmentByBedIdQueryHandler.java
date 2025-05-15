package com.kynsoft.cirugia.application.query.bedassignment;


import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.dto.BedAssignment;
import com.kynsoft.cirugia.domain.dto.PatientDto;
import com.kynsoft.cirugia.domain.service.IBedAssignmentRepository;

import com.kynsoft.cirugia.domain.service.IPatientsService;
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
    private final IPatientsService patientService;
    
    @Override
    public FindLastActiveBedAssignmentByBedIdQueryResult handle(FindLastActiveBedAssignmentByBedIdQuery query) {
        log.info("Buscando última asignación activa para la cama con ID: {}", query.getBedId());
        
        BedAssignment bedAssignment = bedAssignmentRepository.findLastActiveAssignmentByBedId(
                query.getBedId(), query.getBusinessId());
                
        String patientName = "N/A";
        String patientIdentification = "N/A";
        
        if (bedAssignment != null && bedAssignment.getPatientId() != null) {
            PatientDto patientDto = patientService.findById(bedAssignment.getPatientId());
            if (patientDto != null) {
                patientName = patientDto.getName() + " " + patientDto.getLastName();
                patientIdentification = patientDto.getIdentification();
            }
        }
        
        return FindLastActiveBedAssignmentByBedIdQueryResult.builder()
                .bedAssignment(bedAssignment)
                .patientName(patientName)
                .patientIdentification(patientIdentification)
                .build();
    }
}
