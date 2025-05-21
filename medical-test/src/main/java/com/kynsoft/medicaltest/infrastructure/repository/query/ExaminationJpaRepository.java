package com.kynsoft.medicaltest.infrastructure.repository.query;

import com.kynsoft.medicaltest.infrastructure.entities.ExaminationEntity;
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
public interface ExaminationJpaRepository extends JpaRepository<ExaminationEntity, UUID> {
    
    /**
     * Encuentra exámenes por ID de orden
     */
    @Query("SELECT e FROM ExaminationEntity e WHERE e.order.id = :orderId")
    List<ExaminationEntity> findByOrderId(@Param("orderId") UUID orderId);
    
    /**
     * Encuentra exámenes por tipo
     */
    List<ExaminationEntity> findByExaminationType(String examinationType);
    
    /**
     * Encuentra exámenes por estado
     */
    List<ExaminationEntity> findByStatus(String status);
    
    /**
     * Encuentra exámenes completados en un rango de fechas
     */
    List<ExaminationEntity> findByCompletionDateBetween(LocalDateTime start, LocalDateTime end);
}
