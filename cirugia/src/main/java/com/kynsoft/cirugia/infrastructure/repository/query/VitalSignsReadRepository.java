package com.kynsoft.cirugia.infrastructure.repository.query;

import com.kynsoft.cirugia.infrastructure.entities.VitalSignsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VitalSignsReadRepository extends JpaRepository<VitalSignsEntity, UUID>, JpaSpecificationExecutor<VitalSignsEntity> {
    
    List<VitalSignsEntity> findByPatientId(UUID patientId);
    
    List<VitalSignsEntity> findBySurgeryId(UUID surgeryId);
    
    @Query("SELECT v FROM VitalSignsEntity v WHERE v.patientId = :patientId ORDER BY v.recordedAt DESC")
    List<VitalSignsEntity> findLatestByPatientId(@Param("patientId") UUID patientId);
    
    @Query("SELECT v FROM VitalSignsEntity v WHERE v.surgeryId = :surgeryId ORDER BY v.recordedAt DESC")
    List<VitalSignsEntity> findLatestBySurgeryId(@Param("surgeryId") UUID surgeryId);
}