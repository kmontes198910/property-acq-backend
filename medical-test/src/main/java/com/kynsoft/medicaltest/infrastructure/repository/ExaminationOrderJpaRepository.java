package com.kynsoft.medicaltest.infrastructure.repository;

import com.kynsoft.medicaltest.infrastructure.entities.ExaminationOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Interfaz para acceder a la base de datos de órdenes de examen
 */
@Repository
public interface ExaminationOrderJpaRepository extends JpaRepository<ExaminationOrderEntity, UUID> {
    
    /**
     * Encuentra órdenes por ID del paciente
     */
    List<ExaminationOrderEntity> findByPatientId(UUID patientId);
    
    /**
     * Encuentra órdenes por ID del doctor
     */
    List<ExaminationOrderEntity> findByDoctorId(UUID doctorId);
    
    /**
     * Encuentra órdenes por estado
     */
    List<ExaminationOrderEntity> findByStatus(String status);
    
    /**
     * Encuentra órdenes creadas en un rango de fechas
     */
    List<ExaminationOrderEntity> findByCreationDateBetween(LocalDateTime start, LocalDateTime end);
    
    /**
     * Encuentra órdenes por ID de negocio
     */
    List<ExaminationOrderEntity> findByBusinessId(UUID businessId);
}
