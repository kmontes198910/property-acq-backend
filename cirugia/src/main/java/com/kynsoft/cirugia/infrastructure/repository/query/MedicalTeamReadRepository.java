package com.kynsoft.cirugia.infrastructure.repository.query;

import com.kynsoft.cirugia.infrastructure.entities.MedicalTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MedicalTeamReadRepository extends JpaRepository<MedicalTeamEntity, UUID>, JpaSpecificationExecutor<MedicalTeamEntity> {
    
    List<MedicalTeamEntity> findBySurgeryId(UUID surgeryId);
    
    List<MedicalTeamEntity> findByMemberId(UUID memberId);
    
    List<MedicalTeamEntity> findByBusinessId(UUID businessId);
    
    List<MedicalTeamEntity> findByRole(String role);
    
    @Query("SELECT e FROM MedicalTeamEntity e WHERE e.surgeryId = :surgeryId AND e.role = :role")
    List<MedicalTeamEntity> findBySurgeryIdAndRole(@Param("surgeryId") UUID surgeryId, @Param("role") String role);
    
    @Query("SELECT e FROM MedicalTeamEntity e WHERE e.specialtyCode = :specialtyCode AND e.businessId = :businessId")
    List<MedicalTeamEntity> findBySpecialtyAndBusinessId(@Param("specialtyCode") String specialtyCode, @Param("businessId") UUID businessId);
}