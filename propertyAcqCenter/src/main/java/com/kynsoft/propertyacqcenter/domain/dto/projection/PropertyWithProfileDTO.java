package com.kynsoft.propertyacqcenter.domain.dto.projection;

import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PropertyWithProfileDTO {
    private Property property;
    private String profile;
}
