package com.kynsof.hospitalizationService.infrastructure.repositories.query;

import com.kynsof.hospitalizationService.infrastructure.entity.Hospitalization;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

public interface HospitalizationReadDataJPARepository extends JpaRepository<Hospitalization, UUID>, JpaSpecificationExecutor<Hospitalization> {
    @EntityGraph(attributePaths = {"patient", "attendingDoctor", "emergencyCase", "emergencyCase.patient"})
    @Override
    Page<Hospitalization> findAll(Specification specification, Pageable pageable);

    @EntityGraph(attributePaths = {"patient", "attendingDoctor", "emergencyCase", "emergencyCase.patient"})
    @Override
    Optional<Hospitalization> findById(UUID id);
}
