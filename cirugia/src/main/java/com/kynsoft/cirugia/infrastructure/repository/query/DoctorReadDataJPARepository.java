package com.kynsoft.cirugia.infrastructure.repository.query;

import com.kynsoft.cirugia.infrastructure.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface DoctorReadDataJPARepository extends JpaRepository<Doctor, UUID>, JpaSpecificationExecutor<Doctor> {
    @Override
    Page<Doctor> findAll(Specification specification, Pageable pageable);
}