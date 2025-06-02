package com.kynsoft.medicaltest.infrastructure.repository.query;

import com.kynsoft.medicaltest.infrastructure.entities.LabTestItemRequestEntity;
import com.kynsoft.medicaltest.infrastructure.entities.LabTestParameterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio JPA para la entidad LabTestParameterEntity
 */
@Repository
public interface LabTestParameterReadJpaRepository extends JpaRepository<LabTestParameterEntity, UUID> {
    
    /**
     * Busca parámetros por el ID del examen de laboratorio
     * 
     * @param labTestId El ID del examen de laboratorio
     * @return Lista de parámetros asociados al examen
     */
    List<LabTestParameterEntity> findByLabTestId(UUID labTestId);
    Page<LabTestParameterEntity> findAll(Specification specification, Pageable pageable);
}
