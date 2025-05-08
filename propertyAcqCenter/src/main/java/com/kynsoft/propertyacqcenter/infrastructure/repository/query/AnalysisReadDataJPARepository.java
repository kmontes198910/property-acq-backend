package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.Analysis;
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
public interface AnalysisReadDataJPARepository extends JpaRepository<Analysis, UUID>, JpaSpecificationExecutor<Analysis> {
    @EntityGraph(attributePaths = {"property", "opportunity", "mortageDebt", "compsAtAGlance", "lastSale", "saleValue", "taxAssessments", "comparables"})
    @Override
    Page<Analysis> findAll(Specification<Analysis> specification, Pageable pageable);

    @Override
    Optional<Analysis> findById(UUID id);
}
