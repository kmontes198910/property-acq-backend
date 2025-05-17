package com.kynsoft.medicaltest.domain.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase de dominio que representa un examen médico específico
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Examination {
    
    /**
     * Identificador único del examen
     */
    private UUID id;
    
    /**
     * Identificador de la orden a la que pertenece este examen
     */
    private UUID orderId;
    
    /**
     * Unique code of the examination
     */
    private String code;
    
    /**
     * Tipo de examen (código o nombre del examen)
     */
    private String examinationType;
    
    /**
     * Estado del examen (PENDIENTE, EN_PROCESO, COMPLETADO, etc.)
     */
    private String status;
    
    /**
     * Resultados del examen
     */
    private String results;
    
    /**
     * Fecha de finalización del examen
     */
    private LocalDateTime completionDate;
    
    /**
     * Observaciones o notas sobre el examen
     */
    private String observations;
    
    /**
     * Fecha y hora de creación del registro
     */
    private LocalDateTime createdAt;
    
    /**
     * Fecha y hora de la última actualización
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
    
    /**
     * Verifica si el examen está completado
     */
    public boolean isCompleted() {
        return "COMPLETADO".equals(this.status);
    }
    
    /**
     * Marca el examen como completado
     */
    public void complete(String results) {
        this.results = results;
        this.status = "COMPLETADO";
        this.completionDate = LocalDateTime.now();
    }
}
