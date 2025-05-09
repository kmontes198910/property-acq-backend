package com.kynsoft.invoiceservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DetalleFacturaDTO {
    private String codigoPrincipal;
    private String codigoAuxiliar;
    private String descripcion;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private String tipoImpuesto;
    private BigDecimal porcentajeImpuesto;
    private String codigoImpuestoICE;
    private BigDecimal porcentajeImpuestoICE;
    private String unidadMedida;
    private BigDecimal descuento;
}