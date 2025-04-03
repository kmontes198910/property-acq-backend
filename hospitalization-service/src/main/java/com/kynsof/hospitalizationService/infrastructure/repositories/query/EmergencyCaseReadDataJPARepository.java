package com.kynsof.hospitalizationService.infrastructure.repositories.query;

import com.kynsof.hospitalizationService.infrastructure.entity.EmergencyCase;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmergencyCaseReadDataJPARepository extends JpaRepository<EmergencyCase, UUID>, JpaSpecificationExecutor<EmergencyCase> {
    @Override
    @EntityGraph(attributePaths = {"patient"})
    Page<EmergencyCase> findAll(Specification specification, Pageable pageable);

    @Query("SELECT p FROM EmergencyCase p WHERE p.id = :id")
    Optional<EmergencyCase> findByIdForUpdate(@Param("id") UUID id);

    @EntityGraph(attributePaths = {"patient"})
    Optional<EmergencyCase> findById(UUID id);
}
