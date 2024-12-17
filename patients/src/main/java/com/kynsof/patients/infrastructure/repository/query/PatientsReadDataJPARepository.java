package com.kynsof.patients.infrastructure.repository.query;

import com.kynsof.patients.infrastructure.entity.Patients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PatientsReadDataJPARepository extends JpaRepository<Patients, UUID>, JpaSpecificationExecutor<Patients> {
    Page<Patients> findAll(Specification specification, Pageable pageable);

    Optional<Patients> findByIdentification(String identification);

    @Query("SELECT COUNT(p) FROM Patients p WHERE p.identification = :identification AND p.id <> :id")
    Long countByIdentificationAndNotId(@Param("identification") String identification, @Param("id") UUID id);

    @Query("SELECT COUNT(p) FROM Patients p")
    Long countPatient();

}
