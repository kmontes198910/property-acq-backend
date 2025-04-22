package com.kynsoft.notification.infrastructure.entity;

import com.kynsof.share.core.domain.BaseEntity;
import com.kynsoft.notification.domain.dto.AFileDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AFile extends BaseEntity {

    private String name;
    private String url;
    private String objetId;
    private String path;
    private String userId;
    private String userName;
    private String mimeType;
    private Long size;
    private String secureViewUrl;
    
    @Column(name = "object_id")
    private UUID objectId;
    
    private String objectType;
    
    @Column(name = "business_id")
    private UUID businessId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public AFile(AFileDto file) {
        this.id = file.getId();
        this.name = file.getName();
        this.url = file.getUrl();
        this.objetId = file.getObjetId();
        this.path = file.getPath();
        this.userId = file.getUserId();
        this.userName = file.getUserName();
        this.mimeType = file.getMimeType();
        this.size = file.getSize();
        this.objectId = file.getObjectId();
        this.objectType = file.getObjectType();
        this.businessId = file.getBusinessId();
        this.secureViewUrl = file.getSecureViewUrl();
    }

    @Override
    public UUID getId() {
        return super.getId();
    }

    public AFileDto toAggregate() {
        AFileDto dto = new AFileDto(id, name, url, objetId, path, userId, userName);
        dto.setMimeType(mimeType);
        dto.setSize(size);
        dto.setObjectId(objectId);
        dto.setObjectType(objectType);
        dto.setBusinessId(businessId);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedAt(updatedAt);
        dto.setSecureViewUrl(secureViewUrl);
        return dto;
    }
}