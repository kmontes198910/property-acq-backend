package com.kynsoft.propertyacqcenter.domain.dto;

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
public class AcquisitionDetailsDto {

    private UUID id;
    private LocalDate contractExecutionDate;
    private AcquisitionType acquisitionType; // Purchase, Assignment, Inherited, JV
    private SourceType sourceType; // Broker, Wholesaler, Direct-to-Seller, etc.
    private LegalEntityDto sellerName;//Relacion con Legal entity
    private CompanyDto sellerContactInfo;//Relacion con Legal entity
    private Boolean emdRequirements;
    private Double emdOfferedAmount;
    private LocalDate expectedClosingDate;
    private PropertyDto property;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    private Boolean floodZoneDetermination;//
    private Boolean propertyRented;//
    private Double askingPrice;//
    private Double purchasePrice;//
    private Double rentalPrice;//
    private Double afterRepairValue;//

}
