package com.kynsof.hospitalizationService.infrastructure.repositories.query;

import com.kynsof.hospitalizationService.infrastructure.entity.MedicalEvolution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface MedicalEvolutionReadDataJPARepository extends JpaRepository<MedicalEvolution, UUID>, JpaSpecificationExecutor<MedicalEvolution> {
    Page<MedicalEvolution> findAll(Specification specification, Pageable pageable);
}
