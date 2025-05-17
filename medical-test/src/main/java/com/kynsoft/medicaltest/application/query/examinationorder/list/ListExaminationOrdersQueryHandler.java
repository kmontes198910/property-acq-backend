package com.kynsoft.medicaltest.application.query.examinationorder.list;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.medicaltest.application.query.examinationorder.ExaminationOrderListResponse;
import com.kynsoft.medicaltest.application.query.examinationorder.ExaminationOrderResponse;
import com.kynsoft.medicaltest.domain.entity.Examination;
import com.kynsoft.medicaltest.domain.entity.ExaminationOrder;
import com.kynsoft.medicaltest.domain.service.ExaminationOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListExaminationOrdersQueryHandler implements IQueryHandler<ListExaminationOrdersQuery, ExaminationOrderListResponse> {
    private final ExaminationOrderService orderService;
    
    @Override
    public ExaminationOrderListResponse handle(ListExaminationOrdersQuery query) {
        List<ExaminationOrder> orders;
        
        if (query.getPatientId() != null) {
            orders = orderService.findOrdersByPatientId(query.getPatientId());
        } else if (query.getDoctorId() != null) {
            orders = orderService.findOrdersByDoctorId(query.getDoctorId());
        } else if (query.getStatus() != null) {
            orders = orderService.findOrdersByStatus(query.getStatus());
        } else if (query.getBusinessId() != null) {
            orders = orderService.findOrdersByBusinessId(query.getBusinessId());
        } else if (query.getStartDate() != null && query.getEndDate() != null) {
            orders = orderService.findOrdersByDateRange(query.getStartDate(), query.getEndDate());
        } else {
            // Si no se proporciona ningún filtro, listar órdenes creadas en las últimas 24 horas
            orders = orderService.findOrdersByDateRange(
                    query.getEndDate().minusDays(1),
                    query.getEndDate()
            );
        }
        
        List<ExaminationOrderResponse> responseList = orders.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return new ExaminationOrderListResponse(responseList);
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
                .map(examination -> examination.getId())
                .collect(Collectors.toList());
    }
}
