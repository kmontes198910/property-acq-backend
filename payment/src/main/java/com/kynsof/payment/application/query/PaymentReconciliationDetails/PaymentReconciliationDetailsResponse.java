package com.kynsof.payment.application.query.PaymentReconciliationDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class PaymentReconciliationDetailsResponse {
    private UUID id;
    private String serviceCode; // Código del servicio
    private String description; // Descripción del servicio
    private double totalAmount; // Total recaudado por este servicio
    private double placetopayAmount; // Monto pagado con PlaceToPay
    private double cashAmount; // Monto pagado en efectivo
    private double transferAmount; // Monto pagado por transferencia
    private int serviceCount; // Cantidad de veces que se pagó este servicio
    private LocalDateTime createdAt;
}
