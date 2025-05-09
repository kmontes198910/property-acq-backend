package com.kynsoft.invoiceservice.infrastructure.entities;

/**
 * Representa los posibles estados de una factura en el ciclo de vida de facturación electrónica
 */
public enum InvoiceStatus {
    DRAFT,              // Borrador - Factura en proceso de creación
    GENERATED,          // Generada - XML generado pero aún no enviado
    SIGNED,             // Firmada - XML firmado electrónicamente
    SENT,               // Enviada - Enviada al SRI pero pendiente de autorización
    AUTHORIZED,         // Autorizada - Aprobada por el SRI
    REJECTED,           // Rechazada - Rechazada por el SRI
    ANNULLED,           // Anulada - Anulada por el emisor
    DELIVERED,          // Entregada - Enviada al cliente/comprador
    ERROR               // Error - Error en el proceso
}