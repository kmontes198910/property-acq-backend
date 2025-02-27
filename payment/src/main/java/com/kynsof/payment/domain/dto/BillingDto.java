package com.kynsof.payment.domain.dto;

import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingDto {

    private  UUID id;
    private  UUID clientId;
    private  UUID businessId;
    private  String code;
    private  String description;
    private  BillingStatus status;
    private boolean isProforma;
    private ClientDto client;
    private BusinessDto business;
    private LocalDateTime createdAt;
    private Double cost;

    public BillingDto(UUID id, UUID clientId, UUID businessId, String code, String description, BillingStatus status, boolean isProforma, Double cost) {
        this.id = id;
        this.clientId = clientId;
        this.businessId = businessId;
        this.code = code;
        this.description = description;
        this.status = status;
        this.isProforma = isProforma;
        this.cost = cost;
    }
}