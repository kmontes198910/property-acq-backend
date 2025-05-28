package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AcquisitionDetailsDto;
import com.kynsoft.propertyacqcenter.domain.enums.AcquisitionType;
import com.kynsoft.propertyacqcenter.domain.enums.SourceType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcquisitionDetailsResponse implements IResponse {
    private UUID id;
    private LocalDate contractExecutionDate;
    private AcquisitionType acquisitionType; // Purchase, Assignment, Inherited, JV
    private SourceType sourceType; // Broker, Wholesaler, Direct-to-Seller, etc.
    private LegalEntityBasicResponse sellerName;//Relacion con Legal entity
    private LegalEntityBasicResponse sellerContactInfo;//Relacion con Legal entity
    private Double askingPrice;
    private Double purchasePrice;
    private LocalDate expectedClosingDate;
    private Double rentalPrice;
    private Boolean emdRequirements;
    private Double emdOfferedAmount;
    private Double afterRepairValue;
    private Boolean floodZoneDetermination;
    private Boolean propertyRented;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public AcquisitionDetailsResponse(AcquisitionDetailsDto dto) {
        this.id = dto.getId();
        this.contractExecutionDate = dto.getContractExecutionDate();
        this.acquisitionType = dto.getAcquisitionType();
        this.sourceType = dto.getSourceType();
        this.sellerName = dto.getSellerName() != null ? new LegalEntityBasicResponse(dto.getSellerName()) : null;
        this.sellerContactInfo = dto.getSellerContactInfo() != null ? new LegalEntityBasicResponse(dto.getSellerContactInfo()) : null;
        this.askingPrice = dto.getAskingPrice();
        this.purchasePrice = dto.getPurchasePrice();
        this.expectedClosingDate = dto.getExpectedClosingDate();
        this.rentalPrice = dto.getRentalPrice();
        this.emdRequirements = dto.getEmdRequirements();
        this.emdOfferedAmount = dto.getEmdOfferedAmount();
        this.afterRepairValue = dto.getAfterRepairValue();
        this.floodZoneDetermination = dto.getFloodZoneDetermination();
        this.propertyRented = dto.getPropertyRented();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }
    
}
