package com.kynsof.hospitalizationService.infrastructure.repositories.query;

import com.kynsof.hospitalizationService.infrastructure.entity.EmergencyCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface EmergencyCaseReadDataJPARepository extends JpaRepository<EmergencyCase, UUID>, JpaSpecificationExecutor<EmergencyCase> {
    Page<EmergencyCase> findAll(Specification specification, Pageable pageable);
}
