package com.kynsof.hospitalizationService.infrastructure.repositories.query;

import com.kynsof.hospitalizationService.infrastructure.entity.EmergencyDischarge;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

public interface EmergencyDischargeReadDataJPARepository extends JpaRepository<EmergencyDischarge, UUID>, JpaSpecificationExecutor<EmergencyDischarge> {
    @EntityGraph(attributePaths = {"emergencyCase", "emergencyCase.patient"})
    @Override
    Page<EmergencyDischarge> findAll(Specification specification, Pageable pageable);

    @EntityGraph(attributePaths = {"emergencyCase", "emergencyCase.patient"})
    @Override
    Optional<EmergencyDischarge> findById(UUID id);
}
