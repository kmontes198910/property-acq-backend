package com.kynsof.treatments.domain.dto.enumDto;

public enum BillingStatus {
    PENDING,        // La factura está pendiente de pago
    PAID,           // La factura ha sido pagada
    CANCELED,       // La factura ha sido cancelada
    OVERDUE,        // La factura está vencida
    IN_PROGRESS,    // La factura está en proceso de generación o validación
    COMPLETED       // La factura ha sido finalizada y cerrada
}
