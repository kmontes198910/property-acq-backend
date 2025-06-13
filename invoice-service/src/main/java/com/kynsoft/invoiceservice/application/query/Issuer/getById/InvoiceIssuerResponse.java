package com.kynsoft.invoiceservice.application.query.Issuer.getById;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.invoiceservice.domain.dto.InvoiceIssuerDto;
import com.kynsoft.invoiceservice.infrastructure.entities.Issuer;
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
    private String colorFactura;
    private Boolean status;
    private Boolean microenterprisesRegime;
    


    
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
                .colorFactura(dto.getColorFactura())
                .logoUrl(dto.getLogoUrl())
                .colorFactura(dto.getColorFactura())
                .microenterprisesRegime(dto.getMicroenterprisesRegime())
                .status(dto.getStatus())
                .build();
    }
}