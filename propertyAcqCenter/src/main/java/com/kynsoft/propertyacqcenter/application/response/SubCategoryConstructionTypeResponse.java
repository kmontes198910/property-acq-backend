package com.kynsoft.propertyacqcenter.application.response;

import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryConstructionTypeDto;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryConstructionTypeResponse {

    private UUID id;
    private String name;

    public SubCategoryConstructionTypeResponse(SubCategoryConstructionTypeDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
    }

}