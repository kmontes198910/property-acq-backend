package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyDto {

    private UUID id;
    private String code;
    private String name;
}