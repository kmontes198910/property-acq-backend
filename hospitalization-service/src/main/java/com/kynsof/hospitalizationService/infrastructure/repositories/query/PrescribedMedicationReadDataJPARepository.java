package com.kynsof.hospitalizationService.infrastructure.repositories.query;

import com.kynsof.hospitalizationService.infrastructure.entity.PrescribedMedication;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

public interface PrescribedMedicationReadDataJPARepository extends JpaRepository<PrescribedMedication, UUID>, JpaSpecificationExecutor<PrescribedMedication> {
    @EntityGraph(attributePaths = {"medicalPrescription", "medicalPrescription.hospitalization", "medicalPrescription.hospitalization.patient", "medicalPrescription.hospitalization.emergencyCase", "medicalPrescription.hospitalization.attendingDoctor"})
    @Override
    Page<PrescribedMedication> findAll(Specification specification, Pageable pageable);

    @EntityGraph(attributePaths = {"medicalPrescription", "medicalPrescription.hospitalization", "medicalPrescription.hospitalization.patient", "medicalPrescription.hospitalization.emergencyCase", "medicalPrescription.hospitalization.attendingDoctor"})
    @Override
    Optional<PrescribedMedication> findById(UUID id);
}
