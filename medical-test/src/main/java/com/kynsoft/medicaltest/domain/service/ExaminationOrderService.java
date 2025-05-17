package com.kynsoft.medicaltest.domain.service;

import com.kynsoft.medicaltest.domain.entity.ExaminationOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz de servicio para operaciones de negocio relacionadas con órdenes de examen
 */
public interface ExaminationOrderService {
    
    /**
     * Crea una nueva orden de examen
     */
    ExaminationOrder createOrder(ExaminationOrder order);
    
    /**
     * Actualiza una orden existente
     */
    ExaminationOrder updateOrder(ExaminationOrder order);
    
    /**
     * Busca una orden por su ID
     */
    Optional<ExaminationOrder> findOrderById(UUID id);
    
    /**
     * Elimina una orden
     */
    void deleteOrder(UUID id);
    
    /**
     * Busca órdenes por ID de paciente
     */
    List<ExaminationOrder> findOrdersByPatientId(UUID patientId);
    
    /**
     * Busca órdenes por ID de doctor
     */
    List<ExaminationOrder> findOrdersByDoctorId(UUID doctorId);
    
    /**
     * Busca órdenes por estado
     */
    List<ExaminationOrder> findOrdersByStatus(String status);
    
    /**
     * Busca órdenes creadas en un rango de fechas
     */
    List<ExaminationOrder> findOrdersByDateRange(LocalDateTime start, LocalDateTime end);
    
    /**
     * Busca órdenes por ID de negocio
     */
    List<ExaminationOrder> findOrdersByBusinessId(UUID businessId);
    
    /**
     * Agrega un examen a una orden
     */
    ExaminationOrder addExaminationToOrder(UUID orderId, UUID examinationId);
    
    /**
     * Elimina un examen de una orden
     */
    ExaminationOrder removeExaminationFromOrder(UUID orderId, UUID examinationId);
}
