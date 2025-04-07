package com.kynsof.hospitalizationService.infrastructure.repositories.query;

import com.kynsof.hospitalizationService.infrastructure.entity.TreatmentPlan;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

public interface TreatmentPlanReadDataJPARepository extends JpaRepository<TreatmentPlan, UUID>, JpaSpecificationExecutor<TreatmentPlan> {
    @EntityGraph(attributePaths = {"emergencyCase", "emergencyCase.patient"})
    @Override
    Page<TreatmentPlan> findAll(Specification specification, Pageable pageable);

    @EntityGraph(attributePaths = {"emergencyCase", "emergencyCase.patient"})
    @Override
    Optional<TreatmentPlan> findById(UUID id);
}
