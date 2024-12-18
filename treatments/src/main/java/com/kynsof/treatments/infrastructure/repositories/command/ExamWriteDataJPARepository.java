package com.kynsof.treatments.infrastructure.repositories.command;

import com.kynsof.treatments.infrastructure.entity.Exam;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExamWriteDataJPARepository extends JpaRepository<Exam, UUID> {

    @Modifying
    @Transactional
    @Query(value = "delete from exam where id = :id", nativeQuery = true)
    void deleteByCustomIdNative(UUID id);
}
