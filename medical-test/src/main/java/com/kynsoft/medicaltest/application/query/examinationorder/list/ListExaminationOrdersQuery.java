package com.kynsoft.medicaltest.application.query.examinationorder.list;

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
public class ListExaminationOrdersQuery implements IQuery {
    private UUID patientId;
    private UUID doctorId;
    private String status;
    private UUID businessId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    public static ListExaminationOrdersQuery byPatientId(UUID patientId) {
        return new ListExaminationOrdersQuery(patientId, null, null, null, null, null);
    }
    
    public static ListExaminationOrdersQuery byDoctorId(UUID doctorId) {
        return new ListExaminationOrdersQuery(null, doctorId, null, null, null, null);
    }
    
    public static ListExaminationOrdersQuery byStatus(String status) {
        return new ListExaminationOrdersQuery(null, null, status, null, null, null);
    }
    
    public static ListExaminationOrdersQuery byBusinessId(UUID businessId) {
        return new ListExaminationOrdersQuery(null, null, null, businessId, null, null);
    }
    
    public static ListExaminationOrdersQuery byDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return new ListExaminationOrdersQuery(null, null, null, null, startDate, endDate);
    }
}
