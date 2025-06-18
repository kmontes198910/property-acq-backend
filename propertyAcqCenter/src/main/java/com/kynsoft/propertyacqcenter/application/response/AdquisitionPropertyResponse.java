package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertyResponse implements IResponse {

    private UUID id;
    private LegalEntityBasicResponse buyer;
    private PropertiesResponse property;
    private CompanyContactResponse contact;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public AdquisitionPropertyResponse(AdquisitionPropertyDto dto) {
        this.id = dto.getId();
        this.buyer = dto.getBuyer() != null ? new LegalEntityBasicResponse(dto.getBuyer()) : null;
        this.property = dto.getProperty() != null ? new PropertiesResponse(dto.getProperty()) : null;
        this.contact = dto.getContact() != null ? new CompanyContactResponse(dto.getContact()) : null;
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

}