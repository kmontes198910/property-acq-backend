package com.kynsoft.propertyacqcenter.application.command.companyAddress.create;

import com.kynsoft.propertyacqcenter.domain.enums.AddressType;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCompanyAddressRequest {

    private UUID company;
    private AddressType addressType;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String nickName;
}
