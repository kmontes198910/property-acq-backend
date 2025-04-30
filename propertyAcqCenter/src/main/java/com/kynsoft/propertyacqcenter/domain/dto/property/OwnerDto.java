package com.kynsoft.propertyacqcenter.domain.dto.property;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {
    private List<String> names;
    private String type;
    private PropertyAddressDto mailingAddress;
}
