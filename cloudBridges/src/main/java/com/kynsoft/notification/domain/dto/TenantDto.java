package com.kynsoft.notification.domain.dto;

import com.kynsoft.notification.infrastructure.entity.Tenant;
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
public class TenantDto {

    private UUID id;
    private String tenantId;
    private OffsetDateTime createdAt;

    public Tenant toAggregate(){
        return new Tenant(this);
    }
}