package com.kynsoft.propertyacqcenter.application.command.acquisitionDetails.create;

import com.kynsoft.propertyacqcenter.domain.enums.AcquisitionType;
import com.kynsoft.propertyacqcenter.domain.enums.SourceType;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAcquisitionDetailsRequest {

    private LocalDate contractExecutionDate;
    private AcquisitionType acquisitionType; // Purchase, Assignment, Inherited, JV
    private SourceType sourceType; // Broker, Wholesaler, Direct-to-Seller, etc.
    private UUID sellerName;//Relacion con Legal entity
    private UUID sellerContactInfo;//Relacion con Company
    private String property;
    private Double askingPrice;
    private Double purchasePrice;
    private LocalDate expectedClosingDate;
    private Double rentalPrice;
    private Boolean emdRequirements;
    private Double emdOfferedAmount;
    private Double afterRepairValue;
    private Boolean floodZoneDetermination;
    private Boolean propertyRented;
}
