package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.ManageRole;
import feign.Param;
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
    Page<ManageRole> findAll(Specification specification, Pageable pageable);

    @Query("SELECT COUNT(b) FROM ManageRole b WHERE b.code = :code AND b.id <> :id")
    Long countByCodeAndNotId(@Param("code") String code, @Param("id") UUID id);

    @EntityGraph(attributePaths = {"documentTypes"})
    @Override
    Optional<ManageRole> findById(UUID id);

}