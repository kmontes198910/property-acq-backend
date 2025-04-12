package com.kynsof.identity.infrastructure.repository.query;

import com.kynsof.identity.infrastructure.entities.UserTypePermission;
import com.kynsof.share.core.domain.EUserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface UserTypePermissionReadDataJPARepository extends JpaRepository<UserTypePermission, UUID>, JpaSpecificationExecutor<UserTypePermission> {

    Page<UserTypePermission> findAll(Specification specification, Pageable pageable);
    List<UserTypePermission> findByUserType(EUserType userType);
}
