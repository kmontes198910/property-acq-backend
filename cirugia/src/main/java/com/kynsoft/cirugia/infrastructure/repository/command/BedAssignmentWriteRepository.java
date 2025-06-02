package com.kynsoft.cirugia.infrastructure.repository.command;

import com.kynsoft.cirugia.infrastructure.entities.BedAssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface BedAssignmentWriteRepository extends JpaRepository<BedAssignmentEntity, UUID> {
    
    @Modifying
    @Transactional
    @Query("UPDATE BedAssignmentEntity b SET b.status = :status WHERE b.id = :id")
    void updateStatus(@Param("id") UUID id, @Param("status") String status);
    
    @Modifying
    @Transactional
    @Query("UPDATE BedAssignmentEntity b SET b.status = 'COMPLETED', b.releaseDate = :releaseDate WHERE b.id = :id")
    void release(@Param("id") UUID id, @Param("releaseDate") LocalDateTime releaseDate);
}
