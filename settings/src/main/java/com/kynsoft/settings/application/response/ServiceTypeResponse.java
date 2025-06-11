package com.kynsoft.settings.application.response;


import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.settings.domain.dto.ServiceTypeDto;
import com.kynsoft.settings.domain.enums.EServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ServiceTypeResponse implements IResponse, Serializable {
    private UUID id;
    private String name;
    private String image;
    private String code;
    private EServiceStatus status;


    public ServiceTypeResponse(ServiceTypeDto object) {
        this.id = object.getId();
        this.name = object.getName();
        this.image = object.getPicture();
        this.code = object.getCode();
        this.status = object.getStatus();
    }

}