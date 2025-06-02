package com.kynsoft.medicaltest.infrastructure.repository.query;

import com.kynsoft.medicaltest.infrastructure.entities.LabTestRequestEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

/**
 * Interfaz para acceder a la base de datos de órdenes de examen
 */
@Repository
public interface LabTestRequestReadRepository extends JpaRepository<LabTestRequestEntity, UUID> {

    Page<LabTestRequestEntity> findAll(Specification specification, Pageable pageable);

    @EntityGraph(attributePaths = {"patient", "examinations"})
    @Override
    Optional<LabTestRequestEntity> findById(UUID id);
}
