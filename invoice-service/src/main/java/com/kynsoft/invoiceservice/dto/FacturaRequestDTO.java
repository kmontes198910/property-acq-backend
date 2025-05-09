package com.kynsoft.invoiceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacturaRequestDTO {
    // Información básica de la factura
    private String ruc;
    private String razonSocial;
    private String nombreComercial;
    private String estab;
    private String ptoEmi;
    private String secuencial;
    private String dirMatriz;
    private String correo;
    private String telefono;
    private String fechaEmision;
    private String obligadoContabilidad;
    private String regimenRimpe;
    
    // Información del comprador
    private String tipoIdentificacionComprador;
    private String razonSocialComprador;
    private String identificacionComprador;
    private String direccionComprador;
    private String correoComprador;
    private String telefonoComprador;
    
    // Valores adicionales
    private BigDecimal propina;
    
    // Detalles, pagos e información adicional
    private List<DetalleFacturaDTO> detalles;
    private List<PagoDTO> pagos;
    private List<CampoAdicionalDTO> infoAdicional;
    
    // Opcional: número de agente de retención y contribuyente especial
    private String agenteRetencion;
    private String contribuyenteEspecial;
}