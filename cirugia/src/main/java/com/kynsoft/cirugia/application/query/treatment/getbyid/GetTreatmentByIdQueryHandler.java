package com.kynsoft.cirugia.application.query.treatment.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.dto.Treatment;
import com.kynsoft.cirugia.domain.service.ITreatmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetTreatmentByIdQueryHandler implements IQueryHandler<GetTreatmentByIdQuery, TreatmentResponse> {

    private final ITreatmentRepository treatmentRepository;

    @Override
    public TreatmentResponse handle(GetTreatmentByIdQuery query) {
        log.info("Obteniendo tratamiento con ID: {}", query.getId());
        
        Optional<Treatment> treatment = treatmentRepository.findById(query.getId().toString());
        
        if (treatment.isEmpty()) {
            throw new RuntimeException("Tratamiento no encontrado con ID: " + query.getId());
        }
        
        return new TreatmentResponse(treatment.get());
    }
}