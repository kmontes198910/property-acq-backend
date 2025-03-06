package com.kynsof.evaluation.infrastructure.repositories.command;

import com.kynsof.evaluation.infrastructure.entity.EvaluationPatientExamAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EvaluationPatientExamAnswerWriteDataJPARepository extends JpaRepository<EvaluationPatientExamAnswer, UUID> {

    @Modifying
    @Query("DELETE FROM EvaluationPatientExamAnswer e WHERE e.patientExam.id = :patientExamId")
    void deleteAllByPatientExamId(@Param("patientExamId") UUID patientExamId);
}
