package com.kynsoft.propertyacqcenter.application.command.property.createFromRentCast;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
/**
 * Command to create a property from RentCast data.
 * This command is used to encapsulate the data needed to create a property
 * and send it to the command handler for processing.
 */
public class CreateRentCastPropertyCommand implements ICommand {

    private List<UUID> ids;
    private List<PropertyResponse> propertyResponses;

    public CreateRentCastPropertyCommand(List<PropertyResponse> propertyResponses) {
        this.propertyResponses = propertyResponses;
    }

    public static CreateRentCastPropertyCommand fromRequest(CreateRentCastPropertyRequest propertyRequest) {
        return new CreateRentCastPropertyCommand(propertyRequest.getPropertyResponses());
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateRentCastPropertyMessage(ids);
    }
}
