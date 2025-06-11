package com.kynsof.hospitalizationService.domain.dto;


import com.kynsof.hospitalizationService.domain.enums.EServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTypeDto  implements Serializable {
    private UUID id;
    private String name;
    private String picture;
    private EServiceStatus status;
    private String code;

}
