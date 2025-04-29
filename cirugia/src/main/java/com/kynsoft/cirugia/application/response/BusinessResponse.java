package com.kynsoft.cirugia.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.BusinessDto;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BusinessResponse implements IResponse {

    private UUID id;
    private String name;
    private String latitude;
    private String longitude;
    private String address;
    private String logo;
    private String phone;
    private String email;
    private String ruc;

    public BusinessResponse(BusinessDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.address = dto.getAddress();
        this.logo = dto.getLogo();
        this.phone = dto.getPhone();
        this.email = dto.getEmail();
        this.ruc = dto.getRuc();
    }

}
