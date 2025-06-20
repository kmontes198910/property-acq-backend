package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertyBasicResponse implements IResponse {

    private UUID id;

    public AdquisitionPropertyBasicResponse(AdquisitionPropertyDto dto) {
        this.id = dto.getId();
    }

}
