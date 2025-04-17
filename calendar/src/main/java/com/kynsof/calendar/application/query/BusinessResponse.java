package com.kynsof.calendar.application.query;

import com.kynsof.calendar.domain.dto.BusinessDto;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class BusinessResponse implements IResponse, Serializable {
    private UUID id;
    private String name;
    private String latitude;
    private String longitude;
    private String address;
    private String logo;
    private String phone;
    private String email;
    private String ruc;

    public BusinessResponse(BusinessDto object) {
        this.id = object.getId();
        this.name = object.getName();
        this.latitude = object.getLatitude();
        this.longitude = object.getLongitude();
        this.logo = object.getLogo();
        this.address = object.getAddress();
        this.phone = object.getPhone();
        this.email = object.getEmail();
        this.ruc = object.getRuc();
    }

}