package com.kynsoft.medicaltest.application.query.examination.list;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.medicaltest.application.query.examination.ExaminationListResponse;
import com.kynsoft.medicaltest.application.query.examination.ExaminationResponse;
import com.kynsoft.medicaltest.domain.entity.Examination;
import com.kynsoft.medicaltest.domain.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListExaminationsQueryHandler implements IQueryHandler<ListExaminationsQuery, ExaminationListResponse> {
    private final ExaminationService examinationService;
    
    @Override
    public ExaminationListResponse handle(ListExaminationsQuery query) {
        List<Examination> examinations;
        
        if (query.getOrderId() != null) {
            examinations = examinationService.findExaminationsByOrderId(query.getOrderId());
        } else if (query.getType() != null) {
            examinations = examinationService.findExaminationsByType(query.getType());
        } else if (query.getStatus() != null) {
            examinations = examinationService.findExaminationsByStatus(query.getStatus());
        } else if (query.getStartDate() != null && query.getEndDate() != null) {
            examinations = examinationService.findExaminationsByCompletionDateRange(query.getStartDate(), query.getEndDate());
        } else {
            // Si no se proporciona ningún filtro, listar exámenes completados en las últimas 24 horas
            examinations = examinationService.findExaminationsByCompletionDateRange(
                    query.getEndDate().minusDays(1),
                    query.getEndDate()
            );
        }
        
        List<ExaminationResponse> responseList = examinations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return new ExaminationListResponse(responseList);
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
