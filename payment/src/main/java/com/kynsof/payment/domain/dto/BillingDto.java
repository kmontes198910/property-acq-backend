package com.kynsof.payment.domain.dto;

import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import com.kynsof.payment.domain.dto.enumDto.TypeOperation;
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
    private UUID userSystemId;
    private String userSystemFullName;
    private TypeOperation typeOperation;

    public BillingDto(UUID id, UUID clientId, UUID businessId, String code, String description, 
            BillingStatus status, boolean isProforma, Double cost, UUID userSystemId, 
            String userSystemFullName, TypeOperation typeOperation) {
        this.id = id;
        this.clientId = clientId;
        this.businessId = businessId;
        this.code = code;
        this.description = description;
        this.status = status;
        this.isProforma = isProforma;
        this.cost = cost;
        this.userSystemId = userSystemId;
        this.userSystemFullName = userSystemFullName;
        this.typeOperation = typeOperation;
    }

    public BillingDto(UUID id, ClientDto client, BusinessDto business, String code, String description,
                      BillingStatus status, boolean isProforma, Double cost, UUID userSystemId,
                      String userSystemFullName, TypeOperation typeOperation) {

        this.id = id;
        this.client = client;
        this.clientId = client != null ? client.getId() : null;
        this.business = business;
        this.businessId = business != null ? business.getId() : null;
        this.code = code;
        this.description = description;
        this.status = status;
        this.isProforma = isProforma;
        this.cost = cost;
        this.userSystemId = userSystemId;
        this.userSystemFullName = userSystemFullName;
        this.typeOperation = typeOperation;
        this.createdAt = LocalDateTime.now();
    }
}