package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryConstructionTypeDto;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "construction_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryConstructionType {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    public SubCategoryConstructionType(SubCategoryConstructionTypeDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
    }

    public SubCategoryConstructionTypeDto toAggregate() {
        return SubCategoryConstructionTypeDto.builder()
                .id(this.id)
                .name(name)
                .build();
    }
}