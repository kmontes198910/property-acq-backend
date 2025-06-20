package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateAdquisitionPropertyCommand implements ICommand {

    private UUID id;
    private UUID buyer;
    private String property;
    private UUID contact;

    private String buyerNameAndYearVehicle;
    private String buyerLicenseTagNo;

    public CreateAdquisitionPropertyCommand(UUID buyer, String property, UUID contact, String buyerNameAndYearVehicle,
                                            String buyerLicenseTagNo) {
        this.id = UUID.randomUUID();
        this.buyer = buyer;
        this.property = property;
        this.contact = contact;
        this.buyerNameAndYearVehicle = buyerNameAndYearVehicle;
        this.buyerLicenseTagNo = buyerLicenseTagNo;
    }

    public static CreateAdquisitionPropertyCommand fromRequest(CreateAdquisitionPropertyRequest request) {
        return new CreateAdquisitionPropertyCommand(
                request.getBuyer(),
                request.getProperty(),
                request.getContact(),
                request.getBuyerNameAndYearVehicle(),
                request.getBuyerLicenseTagNo()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateAdquisitionPropertyMessage(id);
    }
}
