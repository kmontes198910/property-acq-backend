package com.kynsof.payment.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountReconciliationDto {

    private UUID id;
    private String code;
    private String description;
    private double cost;
    private BusinessDto business;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AccountReconciliationDto(UUID id, String code, String description, double cost, BusinessDto business) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.cost = cost;
        this.business = business;
    }

    
}