package com.kynsoft.settings.infrastructure.repository.query;


import com.kynsoft.settings.infrastructure.entity.RecoveryBedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecoveryBedReadRepository extends JpaRepository<RecoveryBedEntity, UUID>, JpaSpecificationExecutor<RecoveryBedEntity> {

    List<RecoveryBedEntity> findByBusinessId(UUID businessId);

    List<RecoveryBedEntity> findByStatus(String status);

    List<RecoveryBedEntity> findByType(String type);

    @Query("SELECT r FROM RecoveryBedEntity r WHERE r.businessId = :businessId AND r.status = 'AVAILABLE'")
    List<RecoveryBedEntity> findAvailableBeds(@Param("businessId") UUID businessId);
}
