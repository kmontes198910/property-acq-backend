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
public class PropertyImagesResponse implements IResponse {
    private UUID id;
    private PropertyDto property;
    private String url;
    private Boolean main;

    public PropertyImagesResponse(PropertyImagesDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty();
        this.url = dto.getUrl();
        this.main = dto.getMain();
    }

}