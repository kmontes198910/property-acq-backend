package com.kynsof.payment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PaymentReconciliationHeaderDto {
    private LocalDateTime startDate;      // Fecha de inicio del rango
    private LocalDateTime endDate;        // Fecha de fin del rango
    private int totalPayments;            // Cantidad total de pagos en el período
    private double totalRevenue;          // Total recaudado en el período
    private LocalDateTime generatedAt;    // Fecha de generación del reporte
    private List<PaymentReconciliationDetailDto> details; // Lista de servicios pagados
}