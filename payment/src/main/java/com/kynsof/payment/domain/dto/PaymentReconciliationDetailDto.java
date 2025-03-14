package com.kynsof.payment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentReconciliationDetailDto {
    private String serviceCode;  // Código del servicio
    private int serviceCount;    // Cantidad de pagos por servicio
    private double totalAmount;  // Total recaudado por ese servicio
}