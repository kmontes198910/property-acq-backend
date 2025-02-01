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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AFile extends BaseEntity {

    private String name;
    private String url;
    private String objetId;

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
    }

    public AFileDto toAggregate () {
        return new AFileDto(id, name,  url, objetId, "");
    }
}