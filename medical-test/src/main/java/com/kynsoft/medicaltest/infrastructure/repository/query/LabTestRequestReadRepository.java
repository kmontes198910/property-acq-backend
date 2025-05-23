package com.kynsoft.medicaltest.infrastructure.repository.query;

import com.kynsoft.medicaltest.infrastructure.entities.LabTestParameterEntity;
import com.kynsoft.medicaltest.infrastructure.entities.LabTestRequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Interfaz para acceder a la base de datos de órdenes de examen
 */
@Repository
public interface LabTestRequestReadRepository extends JpaRepository<LabTestRequestEntity, UUID> {
    
    /**
     * Encuentra órdenes por ID del paciente
     */
    List<LabTestRequestEntity> findByPatientId(UUID patientId);
    
    /**
     * Encuentra órdenes por ID del doctor
     */
    List<LabTestRequestEntity> findByDoctorId(UUID doctorId);
    
    /**
     * Encuentra órdenes por estado
     */
    List<LabTestRequestEntity> findByStatus(String status);
    
    /**
     * Encuentra órdenes creadas en un rango de fechas
     */
    List<LabTestRequestEntity> findByCreationDateBetween(LocalDateTime start, LocalDateTime end);
    
    /**
     * Encuentra órdenes por ID de negocio
     */
    List<LabTestRequestEntity> findByBusinessId(UUID businessId);
    Page<LabTestRequestEntity> findAll(Specification specification, Pageable pageable);
}
