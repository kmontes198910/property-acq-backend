package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty.AdquisitionPropertyTitleCompanyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertyTitleCompanyResponse implements IResponse {

    private AdquisitionDocumentResponse lenderTitleInsurance;
    private AdquisitionDocumentResponse ownerTitleInsurance;

    public AdquisitionPropertyTitleCompanyResponse(AdquisitionPropertyTitleCompanyDto dto) {
        this.lenderTitleInsurance = DocumentMapper.mapDocumentField(dto.getLenderTitleInsurance());
        this.ownerTitleInsurance = DocumentMapper.mapDocumentField(dto.getOwnerTitleInsurance());
    }

}
