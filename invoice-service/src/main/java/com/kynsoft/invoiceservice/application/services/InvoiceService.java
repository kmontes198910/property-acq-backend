package com.kynsoft.invoiceservice.application.services;

import com.kynsoft.invoiceservice.dto.FacturaRequestDTO;
import com.kynsoft.invoiceservice.dto.FacturaResponseDTO;

public interface InvoiceService {
    /**
     * Genera y guarda una factura en el sistema
     * @param facturaRequestDTO Datos para generar la factura
     * @return Respuesta con el estado y clave de acceso de la factura
     */
    FacturaResponseDTO generateInvoice(FacturaRequestDTO facturaRequestDTO);
}