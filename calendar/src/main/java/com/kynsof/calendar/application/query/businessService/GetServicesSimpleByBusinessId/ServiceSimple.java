package com.kynsof.calendar.application.query.businessService.GetServicesSimpleByBusinessId;

import com.kynsof.calendar.domain.dto.enumType.EServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ServiceSimple  implements Serializable {
    private UUID id;
    private String name;
    private EServiceStatus status;
}
