package com.kynsoft.propertyacqcenter.domain.dto.embedded;

import com.kynsoft.propertyacqcenter.domain.enums.OwnerType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OwnerDto {

    private String names;
    private OwnerType type;
    private String status;
    private EmbeddableAddressDto address;
}
