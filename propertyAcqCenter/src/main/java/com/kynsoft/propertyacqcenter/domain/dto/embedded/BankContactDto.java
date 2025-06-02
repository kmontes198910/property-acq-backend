package com.kynsoft.propertyacqcenter.domain.dto.embedded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankContactDto {
    private String name;
    private String phone;
    private String email;
}
