package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessResponse implements IResponse {

    private UUID id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BusinessResponse(BusinessDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
    }

}