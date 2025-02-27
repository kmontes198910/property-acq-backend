package com.kynsof.payment.application.query.billing.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsof.payment.domain.dto.BillingDto;
import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class BillingResponse implements IResponse {

    private UUID id;
    private UUID patientId;
    private UUID businessId;
    private String code;
    private String description;
    private boolean isProforma;
    private BillingStatus status;
    private ClientDto client;
    private LocalDateTime createdAt;
    private Double cost;

    public BillingResponse(BillingDto dto) {
        this.id = dto.getId();
        this.code = dto.getCode();
        this.description = dto.getDescription();
        this.status = dto.getStatus();
        this.businessId = dto.getBusinessId();
        this.patientId = dto.getClientId();
        this.client = dto.getClient();
        this.createdAt = dto.getCreatedAt();
        this.isProforma = dto.isProforma();
        this.cost = dto.getCost();
    }

}
