package com.kynsoft.invoiceservice.domain.exception;

import lombok.Getter;

/**
 * Mensajes de error específicos para el microservicio de facturación
 */
@Getter
public enum DomainErrorInvoiceMessage {
    // Mensajes de error genéricos de facturación
    ALREADY_EXISTS("INVERR-001", "El elemento ya existe en el sistema"),
    NOT_FOUND("INVERR-002", "El elemento no ha sido encontrado"),
    
    // Mensajes de error específicos de facturación
    INVOICE_NOT_FOUND("INVERR-101", "Factura no encontrada"),
    INVOICE_ALREADY_EXISTS("INVERR-102", "Ya existe una factura con el mismo número"),
    PRODUCT_NOT_FOUND("INVERR-103", "Producto no encontrado"),
    PRODUCT_CODE_ALREADY_EXISTS("INVERR-104", "Ya existe un producto con el código principal"),
    INVOICE_ISSUER_NOT_FOUND("INVERR-105", "Emisor de factura no encontrado"),
    INVALID_INVOICE_DATA("INVERR-106", "Datos de factura inválidos"),
    INVALID_TAX_RATE("INVERR-107", "Tasa de impuesto inválida"),
    INVALID_QUANTITY("INVERR-108", "Cantidad inválida"),
    CUSTOMER_NOT_FOUND("INVERR-109", "Cliente no encontrado"),
    PAYMENT_METHOD_NOT_FOUND("INVERR-110", "Método de pago no encontrado"),
    ISSUER_NOT_FOUND("INVERR-111","No se encuentra el emisor de la factura" ),
    INVALID_INVOICE_STATUS("INVERR-112", "Estado de factura inválido"),;

    private final String code;
    private final String message;

    DomainErrorInvoiceMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
}