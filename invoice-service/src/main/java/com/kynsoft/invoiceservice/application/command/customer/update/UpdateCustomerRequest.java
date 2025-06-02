package com.kynsoft.invoiceservice.application.command.customer.update;

import com.kynsoft.invoiceservice.infrastructure.entities.IdentificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerRequest {
    private UUID id;
    private IdentificationType identificationType;
    private String identificationNumber;
    private String businessName;
    private String address;
    private String email;
    private String phoneNumber;
    private Boolean isActive;
}