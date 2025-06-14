package com.kynsoft.invoiceservice.infrastructure.job;

import ec.e.facturacion.sri.modelo.Factura;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ProcessInvoice {
    private UUID invoiceId;
    private Factura factura;
    private InputStream p12Bytes;
    private String p12Password;
    private String invoiceLogo;
}
