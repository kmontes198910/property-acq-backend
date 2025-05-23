package com.kynsoft.medicaltest.infrastructure.repository.query;

import com.kynsoft.medicaltest.infrastructure.entities.LabTestRequestEntity;
import com.kynsoft.medicaltest.infrastructure.entities.LabTestResultEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio JPA para la entidad LabTestResultEntity
 */
@Repository
public interface LabTestResultReadJpaRepository extends JpaRepository<LabTestResultEntity, UUID> {
    
    /**
     * Busca resultados por el ID del item de examen
     * 
     * @param labTestItemId El ID del item de examen
     * @return Lista de resultados asociados al item de examen
     */
    List<LabTestResultEntity> findByLabTestItemId(UUID labTestItemId);
    
    /**
     * Busca resultados por el ID del parámetro de laboratorio
     * 
     * @param labTestParameterId El ID del parámetro
     * @return Lista de resultados asociados al parámetro
     */
    List<LabTestResultEntity> findByLabTestParameterId(UUID labTestParameterId);
    Page<LabTestResultEntity> findAll(Specification specification, Pageable pageable);
}
