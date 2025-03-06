package com.kynsof.evaluation.infrastructure.repositories.query;

import com.kynsof.evaluation.infrastructure.entity.EvaluationQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EvaluationQuestionReadDataJPARepository extends JpaRepository<EvaluationQuestion, UUID>,
        JpaSpecificationExecutor<EvaluationQuestion> {
    @Override
    Page<EvaluationQuestion> findAll(Specification specification, Pageable pageable);
    @Query("SELECT eq FROM EvaluationQuestion eq WHERE eq.code IN :codes")
    List<EvaluationQuestion> findByCodes(@Param("codes") List<String> codes);

}
