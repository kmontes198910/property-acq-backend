package com.kynsoft.propertyacqcenter.application.command.companyAddress.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.AddressType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateCompanyAddressCommand implements ICommand {

    private UUID id;
    private UUID company;
    private AddressType addressType;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String nickName;

    public UpdateCompanyAddressCommand(UUID id, UUID company, AddressType addressType, 
                                       String streetAddress1, String streetAddress2, String city, 
                                       String state, String zipCode, String country,
                                       String nickName) {
        this.id = id;
        this.company = company;
        this.addressType = addressType;
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.nickName = nickName;
    }

    public static UpdateCompanyAddressCommand fromRequest(UpdateCompanyAddressRequest request, UUID id) {
        return new UpdateCompanyAddressCommand(
                id,
                request.getCompany(),
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
        return new UpdateCompanyAddressMessage(id);
    }
}
