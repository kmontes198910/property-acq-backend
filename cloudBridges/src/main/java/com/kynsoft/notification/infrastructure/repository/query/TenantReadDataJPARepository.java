package com.kynsoft.notification.infrastructure.repository.query;

import com.kynsoft.notification.infrastructure.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface TenantReadDataJPARepository extends JpaRepository<Tenant, UUID>, JpaSpecificationExecutor<Tenant> {
    Page<Tenant> findAll(Specification specification, Pageable pageable);
    Optional<Tenant> findByTenantId(String tenantId);
}
