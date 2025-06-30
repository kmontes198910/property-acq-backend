package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyTeamResponse implements IResponse {
    private UUID id;
    private PropertiesBasicResponse property;
    private CompanyContactSearchResponse contact;
    private String profile;

    public PropertyTeamResponse(PropertyTeamDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.contact = dto.getContact() != null ? new CompanyContactSearchResponse(dto.getContact()) : null;
        this.profile = dto.getProfile();
    }

}
