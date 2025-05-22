package com.kynsoft.invoiceservice.application.command.invoice.generate;

import com.kynsoft.invoiceservice.domain.dto.InvoiceDto;
import ec.e.facturacion.sri.modelo.Factura;

import lombok.Builder;
import lombok.Data;

/**
 * Clase auxiliar para transportar el resultado de la fase de preparación de datos
 * para la generación de facturas.
 */
@Data
@Builder
public class InvoicePreparationResult {
    private InvoiceDto invoiceDto;
    private Factura factura;
    private String accessKey;
}
