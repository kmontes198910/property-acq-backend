package com.kynsoft.invoiceservice.domain.dto;

import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceIssuer;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceIssuingSequence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * DTO para representar la información de un Emisor de Facturas (InvoiceIssuer)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceIssuerDto {
    private UUID id;
    private String ruc;
    private String businessName;
    private String commercialName;
    private String currency;
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
    private String digitalCertP12;
    private String digitalCertPassword;
    @Builder.Default
    private List<InvoiceIssuingSequenceDto> sequences = new ArrayList<>();
    
    /**
     * Obtiene el valor del punto de venta
     * 
     * @return Verdadero si es un punto de venta, falso de lo contrario
     */
    public boolean getPointOfSale() {
        return pointOfSale;
    }
    
    /**
     * Convierte una entidad InvoiceIssuer a un DTO
     * 
     * @param issuer Entidad de emisor de facturas
     * @return DTO con los datos del emisor
     */
    public static InvoiceIssuerDto fromEntity(InvoiceIssuer issuer) {
        if (issuer == null) {
            return null;
        }
        
        return InvoiceIssuerDto.builder()
                .id(issuer.getId())
                .ruc(issuer.getRuc())
                .businessName(issuer.getBusinessName())
                .commercialName(issuer.getCommercialName())
                .currency(issuer.getCurrency())
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
                .logoUrl(issuer.getLogoUrl())
                .sendEmails(issuer.getSendEmails())
                .status(issuer.getStatus())
                .digitalCertP12(issuer.getDigitalCertP12())
                .digitalCertPassword(issuer.getDigitalCertPassword())
                .sequences(mapSequencesToDto(issuer.getSequences()))
                .build();
    }
    
    /**
     * Convierte este DTO a una entidad InvoiceIssuer
     * 
     * @return Entidad de emisor de facturas
     */
    public InvoiceIssuer toEntity() {
        InvoiceIssuer issuer = InvoiceIssuer.builder()
                .id(this.id)
                .ruc(this.ruc)
                .businessName(this.businessName)
                .commercialName(this.commercialName)
                .currency(this.currency)
                .establishment(this.establishment)
                .pointOfSale(this.pointOfSale)
                .address(this.address)
                .emissionPoint(this.emissionPoint)
                .email(this.email)
                .phone(this.phone)
                .website(this.website)
                .specialTaxpayer(this.specialTaxpayer)
                .retentionAgent(this.retentionAgent)
                .accountingObligated(this.accountingObligated)
                .microenterprisesRegime(this.microenterprisesRegime)
                .rimpeRegime(this.rimpeRegime)
                .logoUrl(this.logoUrl)
                .sendEmails(this.sendEmails)
                .status(this.status)
                .digitalCertP12(this.digitalCertP12)
                .digitalCertPassword(this.digitalCertPassword)
                .build();
                
        // Agregar las secuencias
        if (this.sequences != null && !this.sequences.isEmpty()) {
            for (InvoiceIssuingSequenceDto sequenceDto : this.sequences) {
                InvoiceIssuingSequence sequence = InvoiceIssuingSequence.builder()
                        .id(sequenceDto.getId())
                        .documentType(sequenceDto.getDocumentType())
                        .currentSequential(sequenceDto.getCurrentSequential())
                        .lastUsedDate(sequenceDto.getLastUsedDate())
                        .isActive(sequenceDto.getIsActive())
                        .build();
                issuer.addSequence(sequence);
            }
        }
        
        return issuer;
    }
    
    /**
     * Convierte una lista de entidades InvoiceIssuingSequence a una lista de DTOs
     * 
     * @param sequences Lista de secuencias de emisión
     * @return Lista de DTOs de secuencias
     */
    private static List<InvoiceIssuingSequenceDto> mapSequencesToDto(List<InvoiceIssuingSequence> sequences) {
        if (sequences == null || sequences.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<InvoiceIssuingSequenceDto> sequenceDtos = new ArrayList<>();
        for (InvoiceIssuingSequence sequence : sequences) {
            InvoiceIssuingSequenceDto dto = InvoiceIssuingSequenceDto.builder()
                    .id(sequence.getId())
                    .documentType(sequence.getDocumentType())
                    .currentSequential(sequence.getCurrentSequential())
                    .lastUsedDate(sequence.getLastUsedDate())
                    .isActive(sequence.getIsActive())
                    .build();
            sequenceDtos.add(dto);
        }
        
        return sequenceDtos;
    }
}
