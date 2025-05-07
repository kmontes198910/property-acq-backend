package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.PropertyImagesDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "property_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyImages {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(name = "url", nullable = false)
    private String url;

    public PropertyImages(PropertyImagesDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
        this.url = dto.getUrl();
    }

    public PropertyImagesDto toAggregateSimple() {
        return PropertyImagesDto.builder()
                .id(this.id)
                .property(property != null ? property.toAggregate() : null)
                .url(url)
                .build();
    }

    public PropertyImagesDto toAggregate() {
        return PropertyImagesDto.builder()
                .id(this.id)
                .url(url)
                .build();
    }

}