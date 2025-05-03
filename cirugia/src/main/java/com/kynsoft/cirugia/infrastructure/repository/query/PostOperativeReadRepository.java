package com.kynsoft.cirugia.infrastructure.repository.query;

import com.kynsoft.cirugia.infrastructure.entities.PostOperativeEntity;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostOperativeReadRepository extends JpaRepository<PostOperativeEntity, UUID>, JpaSpecificationExecutor<PostOperativeEntity> {
    Optional<PostOperativeEntity> findBySurgeryId(UUID surgeryId);
}