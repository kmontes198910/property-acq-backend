package com.kynsof.hospitalizationService.infrastructure.repositories.query;

import com.kynsof.hospitalizationService.infrastructure.entity.Diagnosis;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

public interface DiagnosisReadDataJPARepository extends JpaRepository<Diagnosis, UUID>, JpaSpecificationExecutor<Diagnosis> {
    @EntityGraph(attributePaths = {"emergencyCase", "emergencyCase.patient"})
    @Override
    Page<Diagnosis> findAll(Specification specification, Pageable pageable);

    @EntityGraph(attributePaths = {"emergencyCase", "emergencyCase.patient"})
    @Override
    Optional<Diagnosis> findById(UUID id);
}
