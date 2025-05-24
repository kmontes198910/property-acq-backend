package com.kynsoft.invoiceservice.application.query.invoiceIssuer.getById;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.invoiceservice.domain.dto.InvoiceIssuerDto;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceIssuer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceIssuerResponse implements IResponse {
    private UUID id;
    private String ruc;
    private String businessName;
    private String commercialName;
    private String establishment;
    private boolean pointOfSale;
    private String address;
    private String emissionPoint;
    private String email;
    private String phone;
    private String specialTaxpayer;
    private String retentionAgent;
    private String rimpeRegime;
    private String logoUrl;
    private Boolean status;
    
    /**
     * Obtiene el valor del punto de venta
     * 
     * @return Verdadero si es un punto de venta, falso de lo contrario
     */
    public boolean getPointOfSale() {
        return pointOfSale;
    }
    
    /**
     * Convierte una entidad InvoiceIssuer a una respuesta
     * 
     * @param issuer Entidad de emisor de facturas
     * @return Respuesta con los datos del emisor
     */
    public static InvoiceIssuerResponse fromEntity(InvoiceIssuer issuer) {
        return InvoiceIssuerResponse.builder()
                .id(issuer.getId())
                .ruc(issuer.getRuc())
                .businessName(issuer.getBusinessName())
                .commercialName(issuer.getCommercialName())
                .establishment(issuer.getEstablishment())
                .pointOfSale(issuer.getPointOfSale())
                .address(issuer.getAddress())
                .emissionPoint(issuer.getEmissionPoint())
                .email(issuer.getEmail())
                .phone(issuer.getPhone())
                .specialTaxpayer(issuer.getSpecialTaxpayer())
                .retentionAgent(issuer.getRetentionAgent())
                .rimpeRegime(issuer.getRimpeRegime())
                .logoUrl(issuer.getLogoUrl())
                .status(issuer.getStatus())
                .build();
    }
    
    /**
     * Convierte un DTO InvoiceIssuerDto a una respuesta
     * 
     * @param dto DTO de emisor de facturas
     * @return Respuesta con los datos del emisor
     */
    public static InvoiceIssuerResponse fromDto(InvoiceIssuerDto dto) {
        return InvoiceIssuerResponse.builder()
                .id(dto.getId())
                .ruc(dto.getRuc())
                .businessName(dto.getBusinessName())
                .commercialName(dto.getCommercialName())
                .establishment(dto.getEstablishment())
                .pointOfSale(dto.getPointOfSale())
                .address(dto.getAddress())
                .emissionPoint(dto.getEmissionPoint())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .specialTaxpayer(dto.getSpecialTaxpayer())
                .retentionAgent(dto.getRetentionAgent())
                .rimpeRegime(dto.getRimpeRegime())
                .logoUrl(dto.getLogoUrl())
                .status(dto.getStatus())
                .build();
    }
}