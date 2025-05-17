package com.kynsoft.medicaltest.application.service;

import com.kynsoft.medicaltest.domain.entity.Examination;
import com.kynsoft.medicaltest.domain.repository.ExaminationRepository;
import com.kynsoft.medicaltest.domain.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación del servicio de exámenes
 */
@Service
@RequiredArgsConstructor
public class ExaminationServiceImpl implements ExaminationService {
    
    private final ExaminationRepository examinationRepository;
    
    @Override
    @Transactional
    public Examination createExamination(Examination examination) {
        if (examination.getStatus() == null || examination.getStatus().isEmpty()) {
            examination.setStatus("PENDIENTE");
        }
        return examinationRepository.save(examination);
    }
    
    @Override
    @Transactional
    public Examination updateExamination(Examination examination) {
        return examinationRepository.save(examination);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Examination> findExaminationById(UUID id) {
        return examinationRepository.findById(id);
    }
    
    @Override
    @Transactional
    public void deleteExamination(UUID id) {
        examinationRepository.delete(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Examination> findExaminationsByOrderId(UUID orderId) {
        return examinationRepository.findByOrderId(orderId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Examination> findExaminationsByType(String type) {
        return examinationRepository.findByExaminationType(type);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Examination> findExaminationsByStatus(String status) {
        return examinationRepository.findByStatus(status);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Examination> findExaminationsByCompletionDateRange(LocalDateTime start, LocalDateTime end) {
        return examinationRepository.findByCompletionDateBetween(start, end);
    }
    
    @Override
    @Transactional
    public Examination completeExamination(UUID id, String results) {
        Examination examination = examinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Examen no encontrado: " + id));
                
        examination.complete(results);
        return examinationRepository.save(examination);
    }
}
