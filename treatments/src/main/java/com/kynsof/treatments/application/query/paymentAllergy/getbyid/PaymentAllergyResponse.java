package com.kynsof.treatments.application.query.paymentAllergy.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsof.treatments.domain.dto.Cie10Dto;
import com.kynsof.treatments.domain.dto.PathologicalHistoryDto;
import com.kynsof.treatments.domain.dto.PatientDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class PaymentAllergyResponse implements IResponse {

    private UUID id;
    private PatientDto patient;
    private Cie10Dto cie10;
    private String observations;
    private LocalDateTime createdAt;
    private String status;
    private  String type;

    public PaymentAllergyResponse(PathologicalHistoryDto dto) {
        this.id = dto.getId();
        this.patient = dto.getPatient();
        this.cie10 = dto.getCie10();
        this.observations = dto.getObservations();
        this.createdAt = dto.getCreatedAt();
        this.status = dto.getStatus();
        this.type = dto.getType();
    }

}
