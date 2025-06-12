package com.kynsoft.invoiceservice.application.query.customer.get;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.invoiceservice.infrastructure.entities.IdentificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse implements IResponse {
    private UUID id;
    private IdentificationType identificationType;
    private String identificationNumber;
    private String businessName;
    private String address;
    private String email;
    private String phoneNumber;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CustomerResponse(CustomerDto customer) {
        this.id = customer.getId();
        this.identificationType = customer.getIdentificationType();
        this.identificationNumber = customer.getIdentificationNumber();
        this.businessName = customer.getBusinessName();
        this.address = customer.getAddress();
        this.email = customer.getEmail();
        this.phoneNumber = customer.getPhoneNumber();
        this.isActive = customer.getIsActive();
        this.createdAt = customer.getCreatedAt();
        this.updatedAt = customer.getUpdatedAt();
    }
}