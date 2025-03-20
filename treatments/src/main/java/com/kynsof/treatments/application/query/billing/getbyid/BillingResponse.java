package com.kynsof.treatments.application.query.billing.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsof.treatments.domain.dto.BillingDto;
import com.kynsof.treatments.domain.dto.InsuranceDto;
import com.kynsof.treatments.domain.dto.PatientDto;
import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class BillingResponse implements IResponse {

    private  UUID id;
    private  UUID patientId;
    private  UUID businessId;
    private  String code;
    private  String description;
    private boolean isProforma;
    private BillingStatus status;
    private PatientDto patient;
    private LocalDateTime createdAt;
    private Double cost;
    private InsuranceDto insurance;


    public BillingResponse(BillingDto dto) {
        this.id = dto.getId();
        this.code = dto.getCode();
        this.description = dto.getDescription();
        this.status = dto.getStatus();
        this.businessId = dto.getBusinessId();
        this.patientId = dto.getPatientId();
        this.patient = dto.getPatient();
        this.createdAt = dto.getCreatedAt();
        this.isProforma = dto.isProforma();
        this.cost = dto.getCost();
        this.insurance = dto.getInsurance();
    }

}
