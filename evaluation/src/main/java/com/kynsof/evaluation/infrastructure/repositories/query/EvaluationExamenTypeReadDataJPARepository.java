package com.kynsof.evaluation.infrastructure.repositories.query;

import com.kynsof.evaluation.infrastructure.entity.EvaluationExamenType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface EvaluationExamenTypeReadDataJPARepository extends JpaRepository<EvaluationExamenType, UUID>, JpaSpecificationExecutor<EvaluationExamenType> {
    @Override
    Page<EvaluationExamenType> findAll(Specification specification, Pageable pageable);
}
