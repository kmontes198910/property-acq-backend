package com.kynsoft.medicaltest.application.query.examination.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.medicaltest.application.query.examination.ExaminationResponse;
import com.kynsoft.medicaltest.domain.entity.Examination;
import com.kynsoft.medicaltest.domain.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetExaminationByIdQueryHandler implements IQueryHandler<GetExaminationByIdQuery, ExaminationResponse> {
    private final ExaminationService examinationService;
    
    @Override
    public ExaminationResponse handle(GetExaminationByIdQuery query) {
        Optional<Examination> examinationOptional = examinationService.findExaminationById(query.getId());
        
        if (examinationOptional.isEmpty()) {
            return null;
        }
        
        Examination examination = examinationOptional.get();
        return mapToResponse(examination);
    }
    
    private ExaminationResponse mapToResponse(Examination examination) {
        return new ExaminationResponse(
                examination.getId(),
                examination.getExaminationType(), // Cambiado de getType() a getExaminationType()
                examination.getCode(), // Campo code 
                null, // No existe getName() en Examination
                examination.getObservations(), // Usando getObservations() en lugar de getDescription()
                examination.getStatus(),
                null, // No existe getScheduledDate() en Examination
                examination.getCompletionDate(), // Cambiado de getCompletedDate() a getCompletionDate()
                examination.getResults(),
                examination.getOrderId()
        );
    }
}
