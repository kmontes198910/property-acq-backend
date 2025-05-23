package com.kynsoft.medicaltest.infrastructure.repository.query;

import com.kynsoft.medicaltest.infrastructure.entities.LabTestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio JPA para la entidad LabTestEntity
 */
@Repository
public interface LabTestReadRepository extends JpaRepository<LabTestEntity, UUID> {
    
    /**
     * Busca un examen de laboratorio por su código
     * 
     * @param code El código del examen de laboratorio
     * @return Un Optional con el examen si existe, o vacío si no existe
     */
    Optional<LabTestEntity> findByCode(String code);
    
    /**
     * Busca exámenes de laboratorio por categoría
     * 
     * @param category La categoría de exámenes a buscar
     * @return Lista de exámenes de laboratorio de la categoría especificada
     */
    List<LabTestEntity> findByCategory(String category);

    Page<LabTestEntity> findAll(Specification specification, Pageable pageable);
}
