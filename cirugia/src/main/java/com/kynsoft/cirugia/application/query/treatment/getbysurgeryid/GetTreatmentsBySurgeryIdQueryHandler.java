package com.kynsoft.cirugia.application.query.treatment.getbysurgeryid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.dto.Treatment;
import com.kynsoft.cirugia.domain.service.ITreatmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetTreatmentsBySurgeryIdQueryHandler implements IQueryHandler<GetTreatmentsBySurgeryIdQuery, TreatmentsListResponse> {

    private final ITreatmentRepository treatmentRepository;

    @Override
    public TreatmentsListResponse handle(GetTreatmentsBySurgeryIdQuery query) {
        log.info("Obteniendo tratamientos para la cirugía con ID: {}", query.getSurgeryId());
        
        List<Treatment> treatments = treatmentRepository.findBySurgeryId(query.getSurgeryId());
        
        return new TreatmentsListResponse(treatments);
    }
}