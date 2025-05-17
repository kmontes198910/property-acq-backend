package com.kynsoft.medicaltest.domain.service;

import com.kynsoft.medicaltest.domain.entity.Examination;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz de servicio para operaciones de negocio relacionadas con exámenes
 */
public interface ExaminationService {
    
    /**
     * Crea un nuevo examen
     */
    Examination createExamination(Examination examination);
    
    /**
     * Actualiza un examen existente
     */
    Examination updateExamination(Examination examination);
    
    /**
     * Busca un examen por su ID
     */
    Optional<Examination> findExaminationById(UUID id);
    
    /**
     * Elimina un examen
     */
    void deleteExamination(UUID id);
    
    /**
     * Busca exámenes por ID de orden
     */
    List<Examination> findExaminationsByOrderId(UUID orderId);
    
    /**
     * Busca exámenes por tipo
     */
    List<Examination> findExaminationsByType(String type);
    
    /**
     * Busca exámenes por estado
     */
    List<Examination> findExaminationsByStatus(String status);
    
    /**
     * Busca exámenes completados en un rango de fechas
     */
    List<Examination> findExaminationsByCompletionDateRange(LocalDateTime start, LocalDateTime end);
    
    /**
     * Completa un examen con resultados
     */
    Examination completeExamination(UUID id, String results);
}
