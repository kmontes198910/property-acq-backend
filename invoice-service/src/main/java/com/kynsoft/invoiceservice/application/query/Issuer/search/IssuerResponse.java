package com.kynsoft.invoiceservice.application.query.Issuer.search;

import com.kynsoft.invoiceservice.infrastructure.entities.Issuer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO para representar la información de un Emisor de Facturas (InvoiceIssuer)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssuerResponse {
    private UUID id;
    private String ruc;
    private String businessName;
    private String commercialName;
    private String currency;
    private String colorFactura;
    private String establishment;
    private boolean pointOfSale;
    private String address;
    private String emissionPoint;
    private String email;
    private String phone;
    private String website;
    private String specialTaxpayer;
    private String retentionAgent;
    private Boolean accountingObligated;
    private Boolean microenterprisesRegime;
    private String rimpeRegime;
    private String logoUrl;
    private Boolean sendEmails;
    private Boolean status;


    
    /**
     * Convierte una entidad InvoiceIssuer a un DTO
     * 
     * @param issuer Entidad de emisor de facturas
     * @return DTO con los datos del emisor
     */
    public static IssuerResponse fromEntity(Issuer issuer) {
        if (issuer == null) {
            return null;
        }
        
        return IssuerResponse.builder()
                .id(issuer.getId())
                .ruc(issuer.getRuc())
                .businessName(issuer.getBusinessName())
                .commercialName(issuer.getCommercialName())
                .currency(issuer.getCurrency())
                .colorFactura(issuer.getColorFactura())
                .establishment(issuer.getEstablishment())
                .pointOfSale(issuer.getPointOfSale())
                .address(issuer.getAddress())
                .emissionPoint(issuer.getEmissionPoint())
                .email(issuer.getEmail())
                .phone(issuer.getPhone())
                .website(issuer.getWebsite())
                .specialTaxpayer(issuer.getSpecialTaxpayer())
                .retentionAgent(issuer.getRetentionAgent())
                .accountingObligated(issuer.getAccountingObligated())
                .microenterprisesRegime(issuer.getMicroenterprisesRegime())
                .rimpeRegime(issuer.getRimpeRegime())
                .sendEmails(issuer.getSendEmails())
                .status(issuer.getStatus())
                .build();
    }
    

}
