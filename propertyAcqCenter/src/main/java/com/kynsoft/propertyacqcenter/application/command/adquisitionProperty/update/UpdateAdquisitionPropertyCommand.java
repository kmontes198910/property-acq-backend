package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateAdquisitionPropertyCommand implements ICommand {

    private UUID id;
    private UUID buyer;
    private String property;
    private UUID contact;

    private String buyerNameAndYearVehicle;
    private String buyerLicenseTagNo;

    public UpdateAdquisitionPropertyCommand(UUID id, UUID buyer, String property, UUID contact, String buyerNameAndYearVehicle,
                                            String buyerLicenseTagNo) {
        this.id = id;
        this.buyer = buyer;
        this.property = property;
        this.contact = contact;
        this.buyerLicenseTagNo = buyerLicenseTagNo;
        this.buyerNameAndYearVehicle = buyerNameAndYearVehicle;
    }

    public static UpdateAdquisitionPropertyCommand fromRequest(UpdateAdquisitionPropertyRequest request, UUID id) {
        return new UpdateAdquisitionPropertyCommand(
                id,
                request.getBuyer(),
                request.getProperty(),
                request.getContact(),
                request.getBuyerNameAndYearVehicle(),
                request.getBuyerLicenseTagNo()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateAdquisitionPropertyMessage(id);
    }
}
