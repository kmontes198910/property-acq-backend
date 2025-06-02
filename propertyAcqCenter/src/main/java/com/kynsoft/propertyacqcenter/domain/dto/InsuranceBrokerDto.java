package com.kynsoft.propertyacqcenter.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceBrokerDto {

    private UUID id;
    private LocalDate closingDate;
    private PropertyDto property;
    private LegalEntityDto buyer;//Legal Entity

    private String lenderInfo;//Información de los prestamistas
    private String lenderInsurance;//Información de responsabilidad y seguro contra riesgos del prestamista

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
