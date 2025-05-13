package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryDto;
import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "sub_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategory {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ContactType category;

    public SubCategory(SubCategoryDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.category = dto.getCategory();
    }

    public SubCategoryDto toAggregate() {
        return SubCategoryDto.builder()
                .id(this.id)
                .name(name)
                .category(category)
                .build();
    }
}