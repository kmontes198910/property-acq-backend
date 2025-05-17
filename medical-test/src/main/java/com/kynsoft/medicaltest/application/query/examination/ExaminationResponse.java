package com.kynsoft.medicaltest.application.query.examination;

import com.kynsof.share.core.domain.bus.query.IResponse;
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
public class ExaminationResponse implements IResponse {
    private UUID id;
    private String type;
    private String code;
    private String name;
    private String description;
    private String status;
    private LocalDateTime scheduledDate;
    private LocalDateTime completedDate;
    private String results;
    private UUID orderId;
}
