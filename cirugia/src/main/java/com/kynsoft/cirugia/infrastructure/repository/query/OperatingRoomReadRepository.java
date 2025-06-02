package com.kynsoft.cirugia.infrastructure.repository.query;

import com.kynsoft.cirugia.infrastructure.entities.OperatingRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OperatingRoomReadRepository extends JpaRepository<OperatingRoomEntity, UUID>, JpaSpecificationExecutor<OperatingRoomEntity> {
    
    List<OperatingRoomEntity> findByBusinessId(UUID businessId);
    
    List<OperatingRoomEntity> findByStatus(String status);
    
    List<OperatingRoomEntity> findByType(String type);
    
    @Query("SELECT o FROM OperatingRoomEntity o WHERE o.businessId = :businessId AND o.status = 'AVAILABLE'")
    List<OperatingRoomEntity> findAvailableRooms(@Param("businessId") UUID businessId);
    
    @Query("SELECT o FROM OperatingRoomEntity o WHERE o.floor = :floor AND o.businessId = :businessId")
    List<OperatingRoomEntity> findByFloorAndBusinessId(@Param("floor") String floor, @Param("businessId") UUID businessId);
    
    @Query("SELECT o FROM OperatingRoomEntity o WHERE o.wing = :wing AND o.businessId = :businessId")
    List<OperatingRoomEntity> findByWingAndBusinessId(@Param("wing") String wing, @Param("businessId") UUID businessId);
    
    @Query("SELECT o FROM OperatingRoomEntity o WHERE o.roomNumber = :roomNumber AND o.businessId = :businessId")
    List<OperatingRoomEntity> findByRoomNumberAndBusinessId(@Param("roomNumber") String roomNumber, @Param("businessId") UUID businessId);
}