package com.kynsoft.notification.application;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.notification.domain.dto.TenantDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TenantResponse implements IResponse {

    private UUID id;
    private String tenantId;
    private OffsetDateTime createdAt;

    public TenantResponse(TenantDto dto) {
        this.id = dto.getId();
        this.tenantId = dto.getTenantId();
        this.createdAt = dto.getCreatedAt();
    }

}
