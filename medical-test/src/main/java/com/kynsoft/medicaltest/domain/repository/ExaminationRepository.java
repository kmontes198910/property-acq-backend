package com.kynsoft.medicaltest.domain.repository;

import com.kynsoft.medicaltest.domain.entity.Examination;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz de repositorio para exámenes
 */
public interface ExaminationRepository {
    
    /**
     * Guarda un examen
     */
    Examination save(Examination examination);
    
    /**
     * Busca un examen por su ID
     */
    Optional<Examination> findById(UUID id);
    
    /**
     * Elimina un examen
     */
    void delete(UUID id);
    
    /**
     * Encuentra exámenes por ID de orden
     */
    List<Examination> findByOrderId(UUID orderId);
    
    /**
     * Encuentra exámenes por tipo
     */
    List<Examination> findByExaminationType(String examinationType);
    
    /**
     * Encuentra exámenes por estado
     */
    List<Examination> findByStatus(String status);
    
    /**
     * Encuentra exámenes completados en un rango de fechas
     */
    List<Examination> findByCompletionDateBetween(LocalDateTime start, LocalDateTime end);
}
