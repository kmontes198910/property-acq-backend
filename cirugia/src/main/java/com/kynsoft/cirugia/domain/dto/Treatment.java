package com.kynsoft.cirugia.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase que representa un tratamiento médico asociado a una cirugía.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Treatment {
    
    /**
     * Identificador único del tratamiento
     */
    private UUID id;
    
    /**
     * Identificador de la cirugía a la que está asociado el tratamiento (opcional)
     */
    private UUID surgeryId;
    
    /**
     * Identificador del paciente al que está asociado el tratamiento
     */
    private UUID patientId;

    /**
     * Nombre del tratamiento o medicamento
     */
    private String name;
    
    /**
     * Descripción detallada del tratamiento
     */
    private String description;
    
    /**
     * Cantidad del medicamento a administrar
     */
    private Integer quantity;
    
    /**
     * Unidad de medida del medicamento (mg, ml, etc.)
     */
    private String medicineUnit;
    
    /**
     * Estado actual del tratamiento (PROGRAMADO, EN_PROCESO, COMPLETADO, etc.)
     */
    private String status;
    
    /**
     * Proceso médico asociado al tratamiento
     */
    private String process;
    
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