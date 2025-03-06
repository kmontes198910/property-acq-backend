package com.kynsof.evaluation.infrastructure.repositories.query;

import com.kynsof.evaluation.domain.dto.enumDto.EvaluationExamenType;
import com.kynsof.evaluation.infrastructure.entity.Evaluation;
import com.kynsof.evaluation.infrastructure.entity.EvaluationPatientExam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface EvaluationPatientReadDataJPARepository extends JpaRepository<EvaluationPatientExam, UUID>,
        JpaSpecificationExecutor<EvaluationPatientExam> {
    @Override
    Page<EvaluationPatientExam> findAll(Specification specification, Pageable pageable);
    // Buscar un examen por el ID de la evolución y el tipo de examen
    @Query("SELECT e FROM EvaluationPatientExam e WHERE e.evaluation.id = :evaluationId AND e.examenType = :examenType")
    Optional<EvaluationPatientExam> findByEvaluationIdAndExamType(@Param("evaluationId") UUID evaluationId,
                                                                  @Param("examenType") EvaluationExamenType examenType);
}
