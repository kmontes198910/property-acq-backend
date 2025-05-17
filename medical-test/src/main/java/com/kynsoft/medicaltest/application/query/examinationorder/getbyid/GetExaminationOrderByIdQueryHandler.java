package com.kynsoft.medicaltest.application.query.examinationorder.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.medicaltest.application.query.examinationorder.ExaminationOrderResponse;
import com.kynsoft.medicaltest.domain.entity.Examination;
import com.kynsoft.medicaltest.domain.entity.ExaminationOrder;
import com.kynsoft.medicaltest.domain.service.ExaminationOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetExaminationOrderByIdQueryHandler implements IQueryHandler<GetExaminationOrderByIdQuery, ExaminationOrderResponse> {
    private final ExaminationOrderService orderService;
    
    @Override
    public ExaminationOrderResponse handle(GetExaminationOrderByIdQuery query) {
        Optional<ExaminationOrder> orderOptional = orderService.findOrderById(query.getId());
        
        if (orderOptional.isEmpty()) {
            return null;
        }
        
        ExaminationOrder order = orderOptional.get();
        return mapToResponse(order);
    }
    
    private ExaminationOrderResponse mapToResponse(ExaminationOrder order) {
        return new ExaminationOrderResponse(
                order.getId(),
                order.getPatientId(),
                order.getDoctorId(),
                null, // No existe doctorName en ExaminationOrder
                order.getBusinessId(),
                null, // No existe businessName en ExaminationOrder
                order.getStatus(),
                order.getCreationDate(), // Usando getCreationDate() en lugar de getOrderDate()
                order.getObservations(), // Usando getObservations() en lugar de getNotes()
                extractExaminationIds(order) // Extraer IDs de los exámenes
        );
    }
    
    private List<UUID> extractExaminationIds(ExaminationOrder order) {
        if (order.getExaminations() == null) {
            return new ArrayList<>();
        }
        return order.getExaminations().stream()
                .map(Examination::getId)
                .collect(Collectors.toList());
    }
}
