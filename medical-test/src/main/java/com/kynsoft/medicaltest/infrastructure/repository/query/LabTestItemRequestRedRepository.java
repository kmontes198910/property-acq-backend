package com.kynsoft.medicaltest.infrastructure.repository.query;

import com.kynsoft.medicaltest.infrastructure.entities.LabTestEntity;
import com.kynsoft.medicaltest.infrastructure.entities.LabTestItemRequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Interfaz para acceder a la base de datos de exámenes
 */
@Repository
public interface LabTestItemRequestRedRepository extends JpaRepository<LabTestItemRequestEntity, UUID> {
    
    /**
     * Encuentra exámenes por ID de orden
     */
    @Query("SELECT e FROM LabTestItemRequestEntity e WHERE e.order.id = :orderId")
    List<LabTestItemRequestEntity> findByOrderId(@Param("orderId") UUID orderId);
    
    /**
     * Encuentra exámenes por tipo
     */
    List<LabTestItemRequestEntity> findByExaminationType(String examinationType);
    
    /**
     * Encuentra exámenes por estado
     */
    List<LabTestItemRequestEntity> findByStatus(String status);
    
    /**
     * Encuentra exámenes completados en un rango de fechas
     */
    List<LabTestItemRequestEntity> findByCompletionDateBetween(LocalDateTime start, LocalDateTime end);
    Page<LabTestItemRequestEntity> findAll(Specification specification, Pageable pageable);
}
