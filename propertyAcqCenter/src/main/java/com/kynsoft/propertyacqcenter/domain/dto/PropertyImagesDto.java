package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyImagesDto {
    private UUID id;
    private PropertyDto property;
    private String url;
    private Boolean main;
}