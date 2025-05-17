package com.kynsoft.medicaltest.infrastructure.repository;

import com.kynsoft.medicaltest.domain.entity.ExaminationOrder;
import com.kynsoft.medicaltest.domain.repository.ExaminationOrderRepository;
import com.kynsoft.medicaltest.infrastructure.mapper.ExaminationOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación del repositorio de dominio para órdenes de examen
 */
@Repository
@RequiredArgsConstructor
public class ExaminationOrderRepositoryImpl implements ExaminationOrderRepository {
    
    private final ExaminationOrderJpaRepository jpaRepository;
    private final ExaminationOrderMapper mapper;
    
    @Override
    public ExaminationOrder save(ExaminationOrder order) {
        var entity = mapper.toEntity(order);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<ExaminationOrder> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public List<ExaminationOrder> findByPatientId(UUID patientId) {
        return mapper.toDomainList(jpaRepository.findByPatientId(patientId));
    }
    
    @Override
    public List<ExaminationOrder> findByDoctorId(UUID doctorId) {
        return mapper.toDomainList(jpaRepository.findByDoctorId(doctorId));
    }
    
    @Override
    public List<ExaminationOrder> findByStatus(String status) {
        return mapper.toDomainList(jpaRepository.findByStatus(status));
    }
    
    @Override
    public List<ExaminationOrder> findByCreationDateBetween(LocalDateTime start, LocalDateTime end) {
        return mapper.toDomainList(jpaRepository.findByCreationDateBetween(start, end));
    }
    
    @Override
    public List<ExaminationOrder> findByBusinessId(UUID businessId) {
        return mapper.toDomainList(jpaRepository.findByBusinessId(businessId));
    }
}
