package com.kynsof.treatments.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResultDto {
    private UUID id;
    private String type;
    private String url;
    private String uploadedById;
    private String uploadedByUsername;
    private ExternalConsultationDto externalConsultation;
    private String fileName;
    private String fileType; // Añadido campo para el tipo de archivo
}