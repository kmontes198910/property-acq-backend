package com.kynsoft.settings.application.command.serviceType.update;

import com.kynsoft.settings.domain.enums.EServiceStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateServiceTypeRequest {
    private String name;
    private String image;
    private EServiceStatus status;
}