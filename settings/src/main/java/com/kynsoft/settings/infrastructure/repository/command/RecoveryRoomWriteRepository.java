package com.kynsoft.settings.infrastructure.repository.command;


import com.kynsoft.settings.infrastructure.entity.RecoveryRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecoveryRoomWriteRepository extends JpaRepository<RecoveryRoomEntity, UUID> {

    @Modifying
    @Query("UPDATE RecoveryRoomEntity r SET r.status = :status WHERE r.id = :id")
    void updateStatus(@Param("id") UUID id, @Param("status") String status);

    @Modifying
    @Query("UPDATE RecoveryRoomEntity r SET r.isActive = false WHERE r.id = :id")
    void deactivate(@Param("id") UUID id);

    @Modifying
    @Query("UPDATE RecoveryRoomEntity r SET r.isActive = true WHERE r.id = :id")
    void activate(@Param("id") UUID id);
}