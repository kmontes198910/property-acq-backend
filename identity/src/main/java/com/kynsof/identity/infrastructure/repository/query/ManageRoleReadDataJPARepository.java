package com.kynsof.identity.infrastructure.repository.query;

import com.kynsof.identity.infrastructure.entities.ManageRole;
import feign.Param;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface ManageRoleReadDataJPARepository extends JpaRepository<ManageRole, UUID>, JpaSpecificationExecutor<ManageRole> {

    @Override
    Page<ManageRole> findAll(Specification specification, Pageable pageable);

    @Query("SELECT COUNT(b) FROM ManageRole b WHERE b.code = :code AND b.id <> :id")
    Long countByCodeAndNotId(@Param("code") String code, @Param("id") UUID id);

    @EntityGraph(attributePaths = {"permissions", "permissions.module"})
    @Override
    Optional<ManageRole> findById(UUID id);

    @Query("SELECT r FROM ManageRole r WHERE r.id IN :ids AND r.isDeleted = false")
    List<ManageRole> findByIds(@Param("ids") List<UUID> ids);
}
