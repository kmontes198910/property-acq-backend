package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.externalApi.ExternalProperty;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface ExternalPropertyReadDataJPARepository extends JpaRepository<ExternalProperty, UUID>, JpaSpecificationExecutor<ExternalProperty> {
    @EntityGraph(attributePaths = {"hoa", "features", "taxAssessments", "propertyTaxes", "history", "owner", "owner.names", "owner.mailingAddress"})
    Optional<ExternalProperty> findByPropertyId(String propertyId);
}
