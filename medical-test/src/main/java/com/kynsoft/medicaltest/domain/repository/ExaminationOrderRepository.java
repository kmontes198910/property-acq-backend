package com.kynsoft.medicaltest.domain.repository;

import com.kynsoft.medicaltest.domain.entity.ExaminationOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz de repositorio para órdenes de examen
 */
public interface ExaminationOrderRepository {
    
    /**
     * Guarda una orden de examen
     */
    ExaminationOrder save(ExaminationOrder order);
    
    /**
     * Busca una orden por su ID
     */
    Optional<ExaminationOrder> findById(UUID id);
    
    /**
     * Elimina una orden
     */
    void delete(UUID id);
    
    /**
     * Encuentra órdenes por ID de paciente
     */
    List<ExaminationOrder> findByPatientId(UUID patientId);
    
    /**
     * Encuentra órdenes por ID de doctor
     */
    List<ExaminationOrder> findByDoctorId(UUID doctorId);
    
    /**
     * Encuentra órdenes por estado
     */
    List<ExaminationOrder> findByStatus(String status);
    
    /**
     * Encuentra órdenes creadas en un rango de fechas
     */
    List<ExaminationOrder> findByCreationDateBetween(LocalDateTime start, LocalDateTime end);
    
    /**
     * Encuentra órdenes por ID de negocio
     */
    List<ExaminationOrder> findByBusinessId(UUID businessId);
}
