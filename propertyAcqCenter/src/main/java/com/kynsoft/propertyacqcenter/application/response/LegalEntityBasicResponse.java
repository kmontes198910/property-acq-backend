package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegalEntityBasicResponse implements IResponse {
    private UUID id;
    private String name;
    
    public LegalEntityBasicResponse(LegalEntityDto legalEntityDto) {
        this.id = legalEntityDto.getId();
        this.name = legalEntityDto.getName();
    }

}
