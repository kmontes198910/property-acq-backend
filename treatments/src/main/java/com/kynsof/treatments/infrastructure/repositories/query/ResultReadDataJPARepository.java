package com.kynsof.treatments.infrastructure.repositories.query;

import com.kynsof.treatments.infrastructure.entity.ExternalConsultation;
import com.kynsof.treatments.infrastructure.entity.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface ResultReadDataJPARepository extends JpaRepository<Result, UUID>, JpaSpecificationExecutor<Result> {
    Page<Result> findAll(Specification<Result> specification, Pageable pageable);

    List<Result> findByExternalConsultation(ExternalConsultation externalConsultation);
    List<Result> findByExternalConsultationId(UUID externalConsultationId);

    Page<Result> findByTypeContainingIgnoreCase(String type, Pageable pageable);
    Page<Result> findByUploadedByUsernameContainingIgnoreCase(String username, Pageable pageable);
    Page<Result> findByUrlContainingIgnoreCase(String url, Pageable pageable);
}