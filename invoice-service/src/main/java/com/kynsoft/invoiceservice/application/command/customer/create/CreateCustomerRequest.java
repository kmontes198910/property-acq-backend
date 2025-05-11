package com.kynsoft.invoiceservice.application.command.customer.create;

import com.kynsoft.invoiceservice.infrastructure.entities.IdentificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {
    private IdentificationType identificationType;
    private String identificationNumber;
    private String businessName;
    private String address;
    private String email;
    private String phoneNumber;
    private Boolean isActive;
}