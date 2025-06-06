package com.kynsoft.settings.infrastructure.repository.command;

import com.kynsoft.settings.infrastructure.entity.RecoveryBedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface RecoveryBedWriteRepository extends JpaRepository<RecoveryBedEntity, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE RecoveryBedEntity r SET r.status = :status WHERE r.id = :id")
    void updateStatus(@Param("id") UUID id, @Param("status") String status);
}