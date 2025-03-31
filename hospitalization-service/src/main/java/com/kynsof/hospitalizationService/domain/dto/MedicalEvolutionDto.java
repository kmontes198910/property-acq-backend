package com.kynsof.hospitalizationService.domain.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicalEvolutionDto {
    private UUID id;
    private HospitalizationDto hospitalization;
    private LocalDate recordDate;
    private String observations;
    private String diagnosis;
}
