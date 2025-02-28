package com.kynsof.evaluation.infrastructure.repositories.query;

import com.kynsof.evaluation.infrastructure.entity.Evaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface EvaluationReadDataJPARepository extends JpaRepository<Evaluation, UUID>, JpaSpecificationExecutor<Evaluation> {
    @Override
    Page<Evaluation> findAll(Specification specification, Pageable pageable);
}
