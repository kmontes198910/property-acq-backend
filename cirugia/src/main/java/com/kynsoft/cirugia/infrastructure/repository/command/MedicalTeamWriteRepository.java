package com.kynsoft.cirugia.infrastructure.repository.command;

import com.kynsoft.cirugia.infrastructure.entities.MedicalTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicalTeamWriteRepository extends JpaRepository<MedicalTeamEntity, UUID> {
    
    @Modifying
    @Query("DELETE FROM MedicalTeamEntity e WHERE e.surgeryId = :surgeryId")
    void deleteBySurgeryId(@Param("surgeryId") UUID surgeryId);
    
    @Modifying
    @Query("DELETE FROM MedicalTeamEntity e WHERE e.memberId = :memberId")
    void deleteByMemberId(@Param("memberId") UUID memberId);
}