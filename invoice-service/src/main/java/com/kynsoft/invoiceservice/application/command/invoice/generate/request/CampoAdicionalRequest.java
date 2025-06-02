package com.kynsoft.invoiceservice.application.command.invoice.generate.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampoAdicionalRequest {
    private String nombre;
    private String valor;
}