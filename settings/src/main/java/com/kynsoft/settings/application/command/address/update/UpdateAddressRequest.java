package com.kynsoft.settings.application.command.address.update;

import com.kynsoft.settings.domain.enums.AddressType;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAddressRequest {

    private AddressType addressType;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String nickName;
}
