package com.kynsof.identity.infrastructure.repository.query;

import com.kynsof.identity.infrastructure.entities.UsersRoles;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRolesReadDataJPARepository extends JpaRepository<UsersRoles, UUID>, JpaSpecificationExecutor<UsersRoles> {
    @EntityGraph(attributePaths = {"role", "role.permissions", "role.permissions.module"})
    @Override
    Page<UsersRoles> findAll(Specification specification, Pageable pageable);

    @EntityGraph(attributePaths = {"role", "role.permissions", "role.permissions.module"})
    @Query("SELECT ur FROM UsersRoles ur WHERE ur.user.id = :userId")
    List<UsersRoles> findByUserId(@Param("userId") UUID userId);
}
