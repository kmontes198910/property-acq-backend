package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.OwnerShipLegalEntityDto;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerShipLegalEntityBasicResponse implements IResponse {

    private UUID id;
    private String name;
    private Double ownershipPercentage;

    public OwnerShipLegalEntityBasicResponse(OwnerShipLegalEntityDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.ownershipPercentage = dto.getOwnershipPercentage();
    }

}
