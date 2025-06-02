package com.kynsoft.invoiceservice.dto;

import com.kynsoft.invoiceservice.application.command.invoice.generate.request.CampoAdicionalRequest;
import com.kynsoft.invoiceservice.application.command.invoice.generate.request.DetalleFacturaRequest;
import com.kynsoft.invoiceservice.application.command.invoice.generate.request.PagoRequest;
import com.kynsoft.invoiceservice.infrastructure.entities.IdentificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacturaRequestDTO {
    // ID del emisor de la factura
    private UUID issuerId;
    
    // Información del comprador
    private IdentificationType tipoIdentificacionComprador;
    private String razonSocialComprador;
    private String identificacionComprador;
    private String direccionComprador;
    private String correoComprador;
    private String telefonoComprador;
    
    // Valores adicionales
    private BigDecimal propina;
    
    // Detalles, pagos e información adicional
    private List<DetalleFacturaRequest> detalles;
    private List<PagoRequest> pagos;
    private List<CampoAdicionalRequest> infoAdicional;
    
    // Opcional: número de agente de retención y contribuyente especial
    private String agenteRetencion;
    private String contribuyenteEspecial;
}