package com.kynsof.hospitalizationService.infrastructure.repositories.query;

import com.kynsof.hospitalizationService.infrastructure.entity.HospitalDischargeSummary;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

public interface HospitalDischargeSummaryReadDataJPARepository extends JpaRepository<HospitalDischargeSummary, UUID>, JpaSpecificationExecutor<HospitalDischargeSummary> {
    @EntityGraph(attributePaths = {"hospitalization"})
    @Override
    Page<HospitalDischargeSummary> findAll(Specification specification, Pageable pageable);

    @EntityGraph(attributePaths = {"hospitalization"})
    @Override
    Optional<HospitalDischargeSummary> findById(UUID id);
}
