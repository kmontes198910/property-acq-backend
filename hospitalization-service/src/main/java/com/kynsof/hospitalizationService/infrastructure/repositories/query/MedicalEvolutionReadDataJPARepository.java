package com.kynsof.hospitalizationService.infrastructure.repositories.query;

import com.kynsof.hospitalizationService.infrastructure.entity.MedicalEvolution;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

public interface MedicalEvolutionReadDataJPARepository extends JpaRepository<MedicalEvolution, UUID>, JpaSpecificationExecutor<MedicalEvolution> {
    @EntityGraph(attributePaths = {"hospitalization", "hospitalization.patient", "hospitalization.emergencyCase", "hospitalization.attendingDoctor"})
    @Override
    Page<MedicalEvolution> findAll(Specification specification, Pageable pageable);

    @EntityGraph(attributePaths = {"hospitalization", "hospitalization.patient", "hospitalization.emergencyCase", "hospitalization.attendingDoctor"})
    @Override
    Optional<MedicalEvolution> findById(UUID id);
}
