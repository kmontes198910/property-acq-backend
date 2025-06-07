package com.kynsoft.settings.infrastructure.repository.query;

import com.kynsoft.settings.infrastructure.entity.RecoveryRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecoveryRoomReadRepository extends JpaRepository<RecoveryRoomEntity, UUID>,
        JpaSpecificationExecutor<RecoveryRoomEntity> {

    List<RecoveryRoomEntity> findByBusinessId(UUID businessId);

    List<RecoveryRoomEntity> findByRoomType(String roomType);

    List<RecoveryRoomEntity> findByStatus(String status);

    @Query("SELECT r FROM RecoveryRoomEntity r WHERE r.businessId = :businessId AND r.isActive = true")
    List<RecoveryRoomEntity> findActiveRoomsByBusinessId(@Param("businessId") UUID businessId);

    @Query("SELECT r FROM RecoveryRoomEntity r WHERE r.status = :status AND r.businessId = :businessId")
    List<RecoveryRoomEntity> findByStatusAndBusinessId(@Param("status") String status, @Param("businessId") UUID businessId);

    @Query("SELECT r FROM RecoveryRoomEntity r WHERE r.roomType = :roomType AND r.businessId = :businessId")
    List<RecoveryRoomEntity> findByTypeAndBusinessId(@Param("roomType") String roomType, @Param("businessId") UUID businessId);
}