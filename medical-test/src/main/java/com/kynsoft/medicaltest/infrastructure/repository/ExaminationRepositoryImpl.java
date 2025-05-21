package com.kynsoft.medicaltest.infrastructure.repository;

import com.kynsoft.medicaltest.infrastructure.repository.query.ExaminationJpaRepository;
import com.kynsoft.medicaltest.domain.entity.Examination;
import com.kynsoft.medicaltest.domain.repository.ExaminationRepository;
import com.kynsoft.medicaltest.infrastructure.mapper.ExaminationMapper;
import com.kynsoft.medicaltest.infrastructure.repository.query.ExaminationOrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación del repositorio de dominio para exámenes médicos
 */
@Repository
@RequiredArgsConstructor
public class ExaminationRepositoryImpl implements ExaminationRepository {
    
    private final ExaminationJpaRepository jpaRepository;
    private final ExaminationOrderJpaRepository orderJpaRepository;
    private final ExaminationMapper mapper;
    
    @Override
    public Examination save(Examination examination) {
        // Asegurarnos de que la orden exista antes de guardar el examen
        if (examination.getOrderId() != null) {
            var entity = mapper.toEntity(examination);
            // Establecer la referencia de la orden correctamente
            orderJpaRepository.findById(examination.getOrderId()).ifPresent(entity::setOrder);
            var savedEntity = jpaRepository.save(entity);
            return mapper.toDomain(savedEntity);
        } else {
            throw new IllegalArgumentException("El examen debe tener un ID de orden válido");
        }
    }
    
    @Override
    public Optional<Examination> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public List<Examination> findByOrderId(UUID orderId) {
        return mapper.toDomainList(jpaRepository.findByOrderId(orderId));
    }
    
    @Override
    public List<Examination> findByExaminationType(String examinationType) {
        return mapper.toDomainList(jpaRepository.findByExaminationType(examinationType));
    }
    
    @Override
    public List<Examination> findByStatus(String status) {
        return mapper.toDomainList(jpaRepository.findByStatus(status));
    }
    
    @Override
    public List<Examination> findByCompletionDateBetween(LocalDateTime start, LocalDateTime end) {
        return mapper.toDomainList(jpaRepository.findByCompletionDateBetween(start, end));
    }
}
