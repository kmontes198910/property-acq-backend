package com.kynsof.hospitalizationService.infrastructure.repositories.query;

import com.kynsof.hospitalizationService.infrastructure.entity.MedicalStaff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface MedicalStaffReadDataJPARepository extends JpaRepository<MedicalStaff, UUID>, JpaSpecificationExecutor<MedicalStaff> {
    Page<MedicalStaff> findAll(Specification specification, Pageable pageable);
}
