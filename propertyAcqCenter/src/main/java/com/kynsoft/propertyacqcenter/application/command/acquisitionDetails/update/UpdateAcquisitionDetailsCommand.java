package com.kynsoft.propertyacqcenter.application.command.acquisitionDetails.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.AcquisitionType;
import com.kynsoft.propertyacqcenter.domain.enums.SourceType;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateAcquisitionDetailsCommand implements ICommand {

    private UUID id;
    private LocalDate contractExecutionDate;
    private AcquisitionType acquisitionType; // Purchase, Assignment, Inherited, JV
    private SourceType sourceType; // Broker, Wholesaler, Direct-to-Seller, etc.
    private UUID sellerName;//Relacion con Legal entity
    private UUID sellerContactInfo;//Relacion con Legal entity
    private Double askingPrice;
    private Double purchasePrice;
    private LocalDate expectedClosingDate;
    private Double rentalPrice;
    private Boolean emdRequirements;
    private Double emdOfferedAmount;
    private Double afterRepairValue;
    private Boolean floodZoneDetermination;
    private Boolean propertyRented;
    private String property;

    public UpdateAcquisitionDetailsCommand(UUID id, LocalDate contractExecutionDate, AcquisitionType acquisitionType, 
                                           SourceType sourceType, UUID sellerName, UUID sellerContactInfo, Double askingPrice, 
                                           Double purchasePrice, LocalDate expectedClosingDate, Double rentalPrice, 
                                           Boolean emdRequirements, Double emdOfferedAmount, Double afterRepairValue, 
                                           Boolean floodZoneDetermination, Boolean propertyRented, String property) {
        this.id = id;
        this.contractExecutionDate = contractExecutionDate;
        this.acquisitionType = acquisitionType;
        this.sourceType = sourceType;
        this.sellerName = sellerName;
        this.sellerContactInfo = sellerContactInfo;
        this.askingPrice = askingPrice;
        this.purchasePrice = purchasePrice;
        this.expectedClosingDate = expectedClosingDate;
        this.rentalPrice = rentalPrice;
        this.emdRequirements = emdRequirements;
        this.emdOfferedAmount = emdOfferedAmount;
        this.afterRepairValue = afterRepairValue;
        this.floodZoneDetermination = floodZoneDetermination;
        this.propertyRented = propertyRented;
        this.property = property;
    }

    public static UpdateAcquisitionDetailsCommand fromRequest(UpdateAcquisitionDetailsRequest request, UUID id) {
        return new UpdateAcquisitionDetailsCommand(
                id,
                request.getContractExecutionDate(),
                request.getAcquisitionType(),
                request.getSourceType(),
                request.getSellerName(),
                request.getSellerContactInfo(),
                request.getAskingPrice(),
                request.getPurchasePrice(),
                request.getExpectedClosingDate(),
                request.getRentalPrice(),
                request.getEmdRequirements(),
                request.getEmdOfferedAmount(),
                request.getAfterRepairValue(),
                request.getFloodZoneDetermination(),
                request.getPropertyRented(),
                request.getProperty()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateAcquisitionDetailsMessage(id);
    }
}
