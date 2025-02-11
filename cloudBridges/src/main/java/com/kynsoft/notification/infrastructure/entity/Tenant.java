package com.kynsoft.notification.infrastructure.entity;

import com.kynsoft.notification.domain.dto.TenantDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {

    @Id
    protected UUID id;
    private String tenantId;

    @Column(updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void prePersist() {
        this.createdAt = OffsetDateTime.now(ZoneId.of("UTC"));
    }

    public Tenant(TenantDto dto) {
        this.id = dto.getId();
        this.tenantId = dto.getTenantId();
    }

    public TenantDto toAggregate () {
        return new TenantDto(id, tenantId, createdAt);
    }
}