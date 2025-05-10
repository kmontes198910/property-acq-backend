package com.kynsoft.invoiceservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos del emisor de facturas")
public class InvoiceIssuerDTO {

    @Schema(description = "Identificador único del emisor", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "RUC del emisor", example = "1792345678001")
    private String ruc;

    @Schema(description = "Razón social del emisor", example = "Farmacia MediSalud S.A.")
    private String businessName;

    @Schema(description = "Nombre comercial del emisor", example = "MediSalud")
    private String commercialName;

    @Schema(description = "Establecimiento del emisor", example = "001")
    private String establishment;

    @Schema(description = "Punto de emisión", example = "001")
    private String emissionPoint;

    @Schema(description = "Dirección del emisor", example = "Av. Principal 123, Quito")
    private String address;

    @Schema(description = "Correo electrónico del emisor", example = "facturacion@medisalud.com")
    private String email;

    @Schema(description = "Teléfono del emisor", example = "0991234567")
    private String phone;

    @Schema(description = "Indica si el emisor es punto de venta", example = "true")
    private boolean pointOfSale;

    @Schema(description = "Número de contribuyente especial", example = "12345")
    private String specialTaxpayer;

    @Schema(description = "Código de agente de retención", example = "1")
    private String retentionAgent;

    @Schema(description = "Régimen RIMPE", example = "NINGUNO")
    private String rimpeRegime;

    @Schema(description = "URL del logo del emisor", example = "https://ejemplo.com/logos/medisalud.png")
    private String logoUrl;

    @Schema(description = "Ambiente de facturación (1: pruebas, 2: producción)", example = "1")
    private String environment;

    @Schema(description = "Estado del emisor (activo/inactivo)", example = "true")
    private Boolean status;

    @Schema(description = "Certificado digital en formato p12 (Base64)", example = "MIIEvQIBADANB...")
    private String digitalCertP12;

    @Schema(description = "Contraseña del certificado digital", example = "contraseña123")
    private String digitalCertPassword;
}