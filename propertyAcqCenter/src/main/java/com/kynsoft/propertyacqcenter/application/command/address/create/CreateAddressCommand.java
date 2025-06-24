package com.kynsoft.propertyacqcenter.application.command.address.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.AddressType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateAddressCommand implements ICommand {
    private UUID id;
    private UUID legalEntity;
    private AddressType addressType;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String nickName;

    public CreateAddressCommand(UUID legalEntity, AddressType addressType, String streetAddress1, String streetAddress2, String city, String state, String zipCode, String country, String nickName) {
        this.id = UUID.randomUUID();
        this.legalEntity = legalEntity;
        this.addressType = addressType;
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.nickName = nickName;
    }

    public static CreateAddressCommand fromRequest(CreateAddressRequest request) {
        return new CreateAddressCommand(
                request.getLegalEntity(),
                request.getAddressType(),
                request.getStreetAddress1(),
                request.getStreetAddress2(),
                request.getCity(),
                request.getState(),
                request.getZipCode(),
                request.getCountry(),
                request.getNickName()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateAddressMessage(id);
    }
}
