package com.kynsof.hospitalizationService.domain.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UbicationDto {
    private UUID id;
    private String code;
    private String name;
}
