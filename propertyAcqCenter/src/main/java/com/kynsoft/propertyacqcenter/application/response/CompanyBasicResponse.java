package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompanyBasicResponse implements IResponse {

    private UUID id;
    private String title;;

    public CompanyBasicResponse(CompanyDto dto) {
        this.id = dto.getId();
        this.title = dto.getTitle();
    }
}
