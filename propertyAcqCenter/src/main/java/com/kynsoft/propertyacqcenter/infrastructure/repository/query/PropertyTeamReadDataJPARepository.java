package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.PropertyTeam;
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
public interface PropertyTeamReadDataJPARepository extends JpaRepository<PropertyTeam, UUID>, JpaSpecificationExecutor<PropertyTeam> {
    @EntityGraph(attributePaths = {"property", "contact"})
    @Override
    Page<PropertyTeam> findAll(Specification<PropertyTeam> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"property", "contact"})
    @Override
    Optional<PropertyTeam> findById(UUID id);
    Optional<PropertyTeam> findByPropertyId(String propertyId);
}
