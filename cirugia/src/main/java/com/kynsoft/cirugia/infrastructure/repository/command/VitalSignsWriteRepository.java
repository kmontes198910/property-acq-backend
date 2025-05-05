package com.kynsoft.cirugia.infrastructure.repository.command;

import com.kynsoft.cirugia.infrastructure.entities.VitalSignsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VitalSignsWriteRepository extends JpaRepository<VitalSignsEntity, UUID> {
    
    @Modifying
    @Query("DELETE FROM VitalSignsEntity v WHERE v.patientId = :patientId")
    void deleteByPatientId(@Param("patientId") UUID patientId);
    
    @Modifying
    @Query("DELETE FROM VitalSignsEntity v WHERE v.surgeryId = :surgeryId")
    void deleteBySurgeryId(@Param("surgeryId") UUID surgeryId);
}