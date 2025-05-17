package com.kynsoft.medicaltest.application.service;

import com.kynsoft.medicaltest.domain.entity.Examination;
import com.kynsoft.medicaltest.domain.entity.ExaminationOrder;
import com.kynsoft.medicaltest.domain.repository.ExaminationOrderRepository;
import com.kynsoft.medicaltest.domain.repository.ExaminationRepository;
import com.kynsoft.medicaltest.domain.service.ExaminationOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación del servicio de órdenes de examen
 */
@Service
@RequiredArgsConstructor
public class ExaminationOrderServiceImpl implements ExaminationOrderService {
    
    private final ExaminationOrderRepository orderRepository;
    private final ExaminationRepository examinationRepository;
    
    @Override
    @Transactional
    public ExaminationOrder createOrder(ExaminationOrder order) {
        if (order.getCreationDate() == null) {
            order.setCreationDate(LocalDateTime.now());
        }
        if (order.getStatus() == null || order.getStatus().isEmpty()) {
            order.setStatus("PENDIENTE");
        }
        return orderRepository.save(order);
    }
    
    @Override
    @Transactional
    public ExaminationOrder updateOrder(ExaminationOrder order) {
        return orderRepository.save(order);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ExaminationOrder> findOrderById(UUID id) {
        return orderRepository.findById(id);
    }
    
    @Override
    @Transactional
    public void deleteOrder(UUID id) {
        orderRepository.delete(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ExaminationOrder> findOrdersByPatientId(UUID patientId) {
        return orderRepository.findByPatientId(patientId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ExaminationOrder> findOrdersByDoctorId(UUID doctorId) {
        return orderRepository.findByDoctorId(doctorId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ExaminationOrder> findOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ExaminationOrder> findOrdersByDateRange(LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByCreationDateBetween(start, end);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ExaminationOrder> findOrdersByBusinessId(UUID businessId) {
        return orderRepository.findByBusinessId(businessId);
    }
    
    @Override
    @Transactional
    public ExaminationOrder addExaminationToOrder(UUID orderId, UUID examinationId) {
        ExaminationOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden de exámenes no encontrada: " + orderId));
                
        Examination examination = examinationRepository.findById(examinationId)
                .orElseThrow(() -> new RuntimeException("Examen no encontrado: " + examinationId));
                
        order.addExamination(examination);
        return orderRepository.save(order);
    }
    
    @Override
    @Transactional
    public ExaminationOrder removeExaminationFromOrder(UUID orderId, UUID examinationId) {
        ExaminationOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden de exámenes no encontrada: " + orderId));
                
        order.removeExamination(examinationId);
        return orderRepository.save(order);
    }
}
