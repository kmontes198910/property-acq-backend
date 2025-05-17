package com.kynsoft.medicaltest.infrastructure.repository.query;

import com.kynsoft.medicaltest.infrastructure.entities.ExaminationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExaminationEntityReadDataJPARepository extends JpaRepository<ExaminationEntity, UUID>, JpaSpecificationExecutor<ExaminationEntity> {
    @Override
    Page<ExaminationEntity> findAll(Specification<ExaminationEntity> specification, Pageable pageable);
}