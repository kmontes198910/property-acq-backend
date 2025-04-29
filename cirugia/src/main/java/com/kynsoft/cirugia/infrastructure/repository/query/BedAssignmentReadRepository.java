package com.kynsoft.cirugia.infrastructure.repository.query;

import com.kynsoft.cirugia.infrastructure.entities.BedAssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BedAssignmentReadRepository extends JpaRepository<BedAssignmentEntity, UUID>, JpaSpecificationExecutor<BedAssignmentEntity> {
    
    List<BedAssignmentEntity> findByPatientId(UUID patientId);
    
    List<BedAssignmentEntity> findBySurgeryId(UUID surgeryId);
    
    List<BedAssignmentEntity> findByBedId(UUID bedId);
    
    List<BedAssignmentEntity> findByStatus(String status);
    
    List<BedAssignmentEntity> findByBusinessId(UUID businessId);
    
    @Query("SELECT b FROM BedAssignmentEntity b WHERE b.businessId = :businessId AND b.status IN ('ASSIGNED', 'IN_USE')")
    List<BedAssignmentEntity> findActiveAssignments(@Param("businessId") UUID businessId);
    
    @Query("SELECT b FROM BedAssignmentEntity b WHERE b.assignmentDate BETWEEN :startDate AND :endDate")
    List<BedAssignmentEntity> findByAssignmentDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT b FROM BedAssignmentEntity b WHERE b.patientId = :patientId ORDER BY b.assignmentDate DESC")
    List<BedAssignmentEntity> findByPatientIdOrderByAssignmentDateDesc(@Param("patientId") UUID patientId);
    
    @Query("SELECT b FROM BedAssignmentEntity b WHERE b.bedId = :bedId AND b.status IN ('ASSIGNED', 'IN_USE')")
    List<BedAssignmentEntity> findActiveBedAssignments(@Param("bedId") UUID bedId);
}
