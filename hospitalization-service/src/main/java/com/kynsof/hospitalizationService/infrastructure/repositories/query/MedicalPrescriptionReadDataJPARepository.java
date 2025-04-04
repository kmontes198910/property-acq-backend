package com.kynsof.hospitalizationService.infrastructure.repositories.query;

import com.kynsof.hospitalizationService.infrastructure.entity.MedicalPrescription;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

public interface MedicalPrescriptionReadDataJPARepository extends JpaRepository<MedicalPrescription, UUID>, JpaSpecificationExecutor<MedicalPrescription> {
    @EntityGraph(attributePaths = {"hospitalization", "hospitalization.patient", "hospitalization.emergencyCase", "hospitalization.emergencyCase.patient", "hospitalization.attendingDoctor"})
    @Override
    Page<MedicalPrescription> findAll(Specification specification, Pageable pageable);

    @EntityGraph(attributePaths = {"hospitalization", "hospitalization.patient", "hospitalization.emergencyCase", "hospitalization.emergencyCase.patient", "hospitalization.attendingDoctor"})
    @Override
    Optional<MedicalPrescription> findById(UUID id);
}
