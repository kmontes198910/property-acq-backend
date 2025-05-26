package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.TeamAssignment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface TeamAssignmentReadDataJPARepository extends JpaRepository<TeamAssignment, UUID>, JpaSpecificationExecutor<TeamAssignment> {
    @EntityGraph(attributePaths = {"property"})
    @Override
    Page<TeamAssignment> findAll(Specification<TeamAssignment> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"property"})
    @Override
    Optional<TeamAssignment> findById(UUID id);
}