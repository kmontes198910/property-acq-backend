package com.kynsoft.medicaltest.application.query.examinationorder;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExaminationOrderResponse implements IResponse {
    private UUID id;
    private UUID patientId;
    private UUID doctorId;
    private String doctorName;
    private UUID businessId;
    private String businessName;
    private String status;
    private LocalDateTime orderDate;
    private String notes;
    private List<UUID> examinationIds;
}
