package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryRealEstateCompanyTypeDto;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryRealEstateCompanyTypeResponse implements IResponse {

    private UUID id;
    private String name;

    public SubCategoryRealEstateCompanyTypeResponse(SubCategoryRealEstateCompanyTypeDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
    }

}