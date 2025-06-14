package com.kynsoft.invoiceservice.application.command.invoice.update.request;

import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInvoiceRequest {
    // ID del emisor de la factura
    private UUID issuerId;

    // Información del comprador usando el nuevo objeto Customer
    private CustomerRequest customer;

    // Valores adicionales
    private BigDecimal propina;

    // Detalles, pagos e información adicional
    private List<DetalleFacturaRequest> detalles;
    private List<PagoRequest> pagos;
    private List<CampoAdicionalRequest> infoAdicional;
    private InvoiceStatus status;

}