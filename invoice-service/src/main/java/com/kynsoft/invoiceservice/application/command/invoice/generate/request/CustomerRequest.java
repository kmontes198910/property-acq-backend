package com.kynsoft.invoiceservice.application.command.invoice.generate.request;

import com.kynsoft.invoiceservice.infrastructure.entities.IdentificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa la información del cliente (comprador) en una factura
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    // Customer identification information
    private IdentificationType identificationType;
    private String businessName;
    private String identificationNumber;
    private String address;
    private String email;
    private String phoneNumber;
}