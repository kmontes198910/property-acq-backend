package com.kynsof.payment.application.query.business.search;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsof.payment.domain.dto.BusinessDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class BusinessResponse implements IResponse {
    private UUID id;
    private String name;
    private String logo;

    public BusinessResponse(BusinessDto object) {
        this.id = object.getId();
        this.name = object.getName();
        this.logo = object.getLogo();
    }

}