package com.kynsof.treatments.infrastructure.repositories.query;

import com.kynsof.treatments.infrastructure.entity.OptometryExam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface OptometryExamenReadDataJPARepository extends JpaRepository<OptometryExam, UUID>, JpaSpecificationExecutor<OptometryExam> {
    Page<OptometryExam> findAll(Specification specification, Pageable pageable);

    Optional<OptometryExam> findTopByExternalConsultationPatientIdAndIsCurrentTrueOrderByCreatedAtDesc(UUID patientId);
}
