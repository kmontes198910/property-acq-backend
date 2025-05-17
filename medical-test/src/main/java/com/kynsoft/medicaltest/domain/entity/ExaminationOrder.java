package com.kynsoft.medicaltest.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Clase de dominio que representa una orden de exámenes médicos
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationOrder {
    
    /**
     * Identificador único de la orden
     */
    private UUID id;
    
    /**
     * Identificador del paciente
     */
    private UUID patientId;
    
    /**
     * Identificador del médico que ordenó los exámenes
     */
    private UUID doctorId;
    
    /**
     * Fecha de creación de la orden
     */
    private LocalDateTime creationDate;
    
    /**
     * Estado de la orden (PENDIENTE, EN_PROCESO, COMPLETADA, etc.)
     */
    private String status;
    
    /**
     * Observaciones o notas adicionales
     */
    private String observations;
    
    /**
     * ID del negocio/sucursal donde se procesa la orden
     */
    private UUID businessId;
    
    /**
     * Lista de exámenes incluidos en la orden
     */
    @Builder.Default
    private List<Examination> examinations = new ArrayList<>();
    
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
     * Agrega un examen a la orden
     */
    public void addExamination(Examination examination) {
        this.examinations.add(examination);
    }
    
    /**
     * Elimina un examen de la orden
     */
    public void removeExamination(UUID examinationId) {
        this.examinations.removeIf(e -> e.getId().equals(examinationId));
    }
}
