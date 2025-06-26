package com.kynsoft.propertyacqcenter.application.response.adquisitionProperty;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.application.response.PropertiesBasicResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.GeneralDocumentDto;
import java.util.List;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertyBaseResponse implements IResponse {

    private UUID id;
    private PropertiesBasicResponse property;
    private List<GeneralDocumentDto> documents;
    private String titleCommitment;

    public AdquisitionPropertyBaseResponse(AdquisitionPropertyDto dto) {
        this.id = dto.getId();
    }

}
