package com.kynsoft.medicaltest.application.query.examination.list;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListExaminationsQuery implements IQuery {
    private UUID orderId;
    private String type;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    public static ListExaminationsQuery byOrderId(UUID orderId) {
        return new ListExaminationsQuery(orderId, null, null, null, null);
    }
    
    public static ListExaminationsQuery byType(String type) {
        return new ListExaminationsQuery(null, type, null, null, null);
    }
    
    public static ListExaminationsQuery byStatus(String status) {
        return new ListExaminationsQuery(null, null, status, null, null);
    }
    
    public static ListExaminationsQuery byDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return new ListExaminationsQuery(null, null, null, startDate, endDate);
    }
}
