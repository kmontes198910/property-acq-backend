package com.kynsoft.cirugia.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase que representa la evolución del paciente después de una cirugía.
 * Contiene información sobre terapias, medicamentos, cuidados generales y otros datos clínicos.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Evolution {
    
    /**
     * Identificador único de la evolución
     */
    private UUID id;
    
    /**
     * Identificador de la cirugía a la que está asociada la evolución
     */
    private UUID surgeryId;
    
    /**
     * Fluidos terapéuticos administrados al paciente
     */
    private String therapeuticFluids;
    
    /**
     * Fluidos prescritos para el paciente
     */
    private String prescriptionFluids;
    
    /**
     * Medidas generales y cuidados para el paciente
     */
    private String generalCare;
    
    /**
     * Datos de monitoreo del paciente
     */
    private String monitoring;
    
    /**
     * Indicaciones de alimentación o dieta
     */
    private String diet;
    
    /**
     * Resultados de análisis o exámenes realizados
     */
    private String analytics;
    
    /**
     * Otras observaciones o indicaciones
     */
    private String others;
    
    /**
     * Fecha y hora en que se registra la evolución
     */
    private LocalDateTime evolutionDate;
    
    /**
     * Fecha y hora de creación del registro
     */
    private LocalDateTime createdAt;
    
    /**
     * Fecha y hora de la última actualización del registro
     */
    private LocalDateTime updatedAt;
    
    /**
     * Identificador del usuario que creó el registro
     */
    private UUID createdBy;
    
    /**
     * Identificador del usuario que realizó la última actualización
     */
    private UUID updatedBy;
}