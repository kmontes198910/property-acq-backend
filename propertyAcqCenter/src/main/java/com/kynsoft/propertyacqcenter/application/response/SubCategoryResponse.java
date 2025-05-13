package com.kynsoft.propertyacqcenter.application.response;

import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryDto;
import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryResponse {

    private UUID id;
    private String name;
    private ContactType category;

    public SubCategoryResponse(SubCategoryDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.category = dto.getCategory();
    }

}