package com.kynsof.evaluation.infrastructure.repositories.query;

import com.kynsof.evaluation.domain.dto.enumDto.EvaluationExamenType;
import com.kynsof.evaluation.infrastructure.entity.EvaluationPatientExam;
import com.kynsof.evaluation.infrastructure.entity.EvaluationPatientExamAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EvaluationPatientExamAnswerReadDataJPARepository extends JpaRepository<EvaluationPatientExamAnswer, UUID>,
        JpaSpecificationExecutor<EvaluationPatientExamAnswer> {
    @Override
    Page<EvaluationPatientExamAnswer> findAll(Specification specification, Pageable pageable);
    @Query("SELECT e FROM EvaluationPatientExamAnswer e WHERE e.patientExam.id = :patientExamId")
    List<EvaluationPatientExamAnswer> findByPatientExamId(@Param("patientExamId") UUID patientExamId);

}
