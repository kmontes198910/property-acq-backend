package com.kynsoft.propertyacqcenter.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TitleRequiredFilesDto {

    private String preliminaryTitleReport;
    private String recordedDeedCopy;
    private String legalDescription;
    private String existingTitlePolicy;
    private boolean reissueCreditAvailable;

    // Documentos legales y financieros
    private String easementsOrRestrictions;
    private String taxCertificates;
    private String uccSearchResults; // Para propiedades comerciales
    private String oldTitleInsurancePolicy;

    // Estado y porcentaje
    private double completionPercentage; // 36.5% como muestra la imagen
    private LocalDate lastUpdated;
}
